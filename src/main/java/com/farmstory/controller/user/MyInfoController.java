package com.farmstory.controller.user;


import com.farmstory.dto.CartDTO;
import com.farmstory.dto.CateDTO;
import com.farmstory.dto.PassResetDTO;
import com.farmstory.dto.UserDTO;
import com.farmstory.security.MyUserDetails;
import com.farmstory.service.CartService;
import com.farmstory.service.CategoryService;
import com.farmstory.service.EmailService;
import com.farmstory.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
public class MyInfoController {

    private final UserService userService;
    private final EmailService emailService;
    private final CategoryService categoryService;
    private final CartService cartService;



    // 비밀번호 수정 API
    @ResponseBody
    @PostMapping("/api/myinfo/modifypass")
    public ResponseEntity<?> modifyPassword(@AuthenticationPrincipal MyUserDetails userDetails, @RequestBody PassResetDTO passResetDTO, HttpSession session) {
        // 만약 로그인된 사용자가 없다면 예외발생
        // userDetails << 로그인된 사용자
        if( userDetails == null ) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다.");
        }
        log.info("passResetDTO : {}",passResetDTO);
        String newPass = passResetDTO.getNewpass();

        if ( newPass != null) {
            // 비밀번호 변경 서비스 호출
            userService.modifyPassword(userDetails.getUsername(), newPass);

            return ResponseEntity.ok("비밀번호가 성공적으로 변경되었습니다.");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("비밀번호 변경에 실패했습니다.");
    }




    // 별명 중복 확인 API
    @GetMapping("/api/user/check-nick")
    @ResponseBody
    public Map<String, Boolean> checkNick(@RequestParam("nick") String nick) {
        boolean isDuplicate = userService.isNickDuplicate(nick);
        Map<String, Boolean> result = new HashMap<>();
        result.put("result", isDuplicate); // 중복일 경우 true, 아니면 false
        return result;
    }



    // 회원정보 수정
    @ResponseBody
    @PostMapping("/api/user/update")
    public ResponseEntity<?> updateUserInfo(@AuthenticationPrincipal MyUserDetails userDetails, @RequestBody UserDTO userDTO, HttpSession session) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 정보가 없습니다.");
        }
        try {
            userDTO.setUid(userDetails.getUsername());
            // 서비스에서 회원 정보 수정 처리
            userService.updateUserInfo(userDTO);
            return ResponseEntity.ok().body("회원 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            log.error("회원 정보 수정 중 오류 발생", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("회원 정보 수정에 실패했습니다.");
        }
    }

    @GetMapping("/category/myInfo/cart")
    public String cart(@RequestParam(value = "content", required = false) String content
            , Model model){

        CateDTO cate = categoryService.selectCategory("myInfo","cart");
        log.info("cate : "+cate);

        List<CartDTO> cartList = cartService.findCartWithUid();

        model.addAttribute("cate", cate);
        model.addAttribute("content", content);

        model.addAttribute("cartList", cartList);

        return "boardIndex";
    }



}
