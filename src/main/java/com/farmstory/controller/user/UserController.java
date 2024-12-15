package com.farmstory.controller.user;

import com.farmstory.dto.*;
import com.farmstory.entity.User;
import com.farmstory.security.MyUserDetails;
import com.farmstory.service.CategoryService;
import com.farmstory.service.EmailService;
import com.farmstory.service.TermsService;
import com.farmstory.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Controller
@Log4j2
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;
    private final CategoryService categoryService;
    private final TermsService termsService;
    private String email;

    @GetMapping("/admin/a_user")
    public String list(Model model, PageRequestDTO pageRequestDTO) {

        log.info("컨트롤러ㅏ에는 들어가나야ㅕ?        ????????????????????????????");
        UserPageResponseDTO userPageResponseDTO = userService.selectUserAll(pageRequestDTO);
        model.addAttribute("userPageResponseDTO", userPageResponseDTO);

        CateDTO cate= categoryService.selectCategory("admin","a_user");

        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);
        return "admin_index";
    }

    @PostMapping("/user/register")
    public String register(UserDTO userDTO, HttpServletRequest req) {
        if(userDTO ==null){
            return "redirect:/category/user/register?success=300";
        }
        String regip = req.getParameter("regip");
        userDTO.setRegip(regip);
        UserDTO savedUser = userService.insertUser(userDTO);
        return "redirect:/category/user/login?success=200";
    }

    @GetMapping("/user/terms")
    public String terms(Model model) {
        TermsDTO termsDTO = termsService.selectTerms();
        model.addAttribute("termsDto", termsService.selectTerms());
        CateDTO cateDTO = categoryService.selectCateNo(702);
        model.addAttribute("cate", cateDTO);
        return "boardIndex";
    }


    // 회원가입 또는 아이디 찾기에서 이메일을 입력받아 인증번호 전송
    @ResponseBody
    @GetMapping("/user/{type}/{value}")
    public ResponseEntity<?> checkUser(@PathVariable("type")  String type,
                                       @PathVariable("value") String value, HttpSession session, Model model)  {

        log.info("type : "+type+", value : "+value);
        int count = userService.selectCountUserByType(type,value);

        log.info("count : "+count);

        //해당되는 유저가 없는 email일때
        // 이메일 인증이 필요한 경우 (회원가입 시 이메일이 중복되지 않거나, 아이디 찾기에서 유효한 이메일일 때),
        if(type.equals("email") && count == 0){
            LocalDateTime requestedAt = LocalDateTime.now();
            String code = emailService.sendMail(value, "contents/user/email",session);
            log.info("value code : "+code);
        }

        // Json 생성
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("result", count);

        log.info(resultMap.toString());

        return ResponseEntity.ok().body(resultMap);

    }

    //이메일 일치 검사
    @ResponseBody
    @PostMapping("/email")
    public ResponseEntity<?> checkUser(@RequestBody Map<String, String> jsonData
            , HttpSession session) {

        log.info("checkEmail code : " + jsonData);

        String receiveCode = jsonData.get("code");
        log.info("checkEmail receiveCode : " + receiveCode);


        String sessionCode = (String) session.getAttribute("code");


        log.info("sessionCode : "+sessionCode);


        // Json 생성
        Map<String, Object> resultMap;
        if(sessionCode.equals(receiveCode)){
            // Json 생성
            resultMap = new HashMap<>();
            resultMap.put("result", true);


        }else{
            // Json 생성
            resultMap = new HashMap<>();
            resultMap.put("result", false);
        }

        session.removeAttribute("code");

        return ResponseEntity.ok().body(resultMap);


    }

    // 아이디 찾기: 이름과 이메일을 입력받아 인증번호를 *이메일로 전송 ("/findid") 괄호 안 경로
    @ResponseBody
    @PostMapping("/api/user/findid")
    public ResponseEntity<?> findUserIdByNameAndEmail(HttpSession session, @RequestBody Map<String, String> jsonData) {
        String name = jsonData.get("name");
        String email = jsonData.get("email");

        // 아이디 찾기 서비스 호출
        userService.receiveCode(name, email);
        log.info("저장한 세션 : {}", session.getAttribute("code"));
        // 응답으로 인증번호 발송 완료 메시지 전송
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    // 이메일 인증 코드 검증 후 아이디 반환
    @ResponseBody
    @PostMapping("/api/user/findidresult")
    public ResponseEntity<?> verifyEmailCode(HttpSession session, @RequestBody Map<String, String> jsonData) {
        String verificationCode = jsonData.get("code");
        String name = jsonData.get("name");
        String email = jsonData.get("email");

        // 세션에서 인증번호 및 사용자 정보 가져오기
        String sessionCode = (String) session.getAttribute("code");
        String sessionName = (String) session.getAttribute("name");
        String sessionEmail = (String) session.getAttribute("email");

        // 인증번호 및 사용자 정보 검증
        if (sessionCode != null && sessionCode.equals(verificationCode)
                && sessionName.equals(name) && sessionEmail.equals(email)) {

            // 인증 성공: 유저 아이디 반환
            User user = userService.verifyCodeForUser(verificationCode, name, email);

            // 성공 응답
            session.setAttribute("user", user);

            return ResponseEntity.status(HttpStatus.OK).build();

        } else {
            // 실패 응답
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("message", "인증번호가 일치하지 않거나 사용자 정보가 올바르지 않습니다.");
            return ResponseEntity.badRequest().body(resultMap);
        }
    }






    @ResponseBody
    @PostMapping("/api/user/findpass")
    public ResponseEntity<?> findUserByUidAndEmail(HttpSession session, @RequestBody Map<String, String> jsonData) {
        String uid = jsonData.get("uid");
        String email = jsonData.get("email");

        // 비밀번호 찾기 서비스 호출 (아이디와 이메일을 받아서 인증 코드를 전송)
        String verificationCode = userService.resetCode(uid, email);

        // 세션에 인증번호 저장
        session.setAttribute("code", verificationCode);
        session.setAttribute("uid", uid);
        session.setAttribute("email", email);

// 로그 추가 - 세션 값 확인
        log.info("세션에 저장된 인증 코드: {}", session.getAttribute("code"));
        log.info("세션에 저장된 UID: {}", session.getAttribute("uid"));
        log.info("세션에 저장된 이메일: {}", session.getAttribute("email"));

        // 응답으로 인증번호 발송 완료 메시지 전송
        return ResponseEntity.status(HttpStatus.OK).body("비밀번호 재설정 인증 코드가 이메일로 전송되었습니다.");
    }

    @ResponseBody
    @PostMapping("/api/user/verifypass")
    public ResponseEntity<?> verifypass(HttpSession session, @RequestBody Map<String, String> jsonData) {
        String verificationCode = jsonData.get("code");
        String uid = jsonData.get("uid");
        String email = jsonData.get("email");

        // 세션에서 인증번호 및 사용자 정보 가져오기
        String sessionCode = (String) session.getAttribute("code");
        String sessionUid = (String) session.getAttribute("uid");
        String sessionEmail = (String) session.getAttribute("email");

        // 로그 추가 - 세션 값과 클라이언트에서 받은 값 확인
        log.info("세션에서 가져온 인증 코드: {}", sessionCode);
        log.info("세션에서 가져온 UID: {}", sessionUid);
        log.info("세션에서 가져온 이메일: {}", sessionEmail);
        log.info("클라이언트로부터 받은 인증 코드: {}", verificationCode);
        log.info("클라이언트로부터 받은 UID: {}", uid);
        log.info("클라이언트로부터 받은 이메일: {}", email);

        // 인증번호 및 사용자 정보 검증
        if (sessionCode != null && sessionCode.equals(verificationCode)
                && sessionUid.equals(uid) && sessionEmail.equals(email)
        ) {



            // 성공 응답
            return ResponseEntity.status(HttpStatus.OK).build();

        } else {
            // 실패 응답

            return ResponseEntity.badRequest().build();
        }
    }

    @ResponseBody
    @PostMapping("/api/user/changepass")
    public ResponseEntity<?> resetPassword(HttpSession session, @RequestBody Map<String, String> jsonData) {
        String uid = jsonData.get("uid");
        String newpass = jsonData.get("newpass");

        // 세션에서 인증번호 및 사용자 정보 가져오기
        String sessionCode = (String) session.getAttribute("code");
        String sessionUid = (String) session.getAttribute("uid");


        System.out.println("sessionUid = " + sessionUid);
        System.out.println("uid = " + uid);



        // 인증번호 및 사용자 정보 검증
        if (sessionUid.equals(uid)) {
            // 인증 성공: 비밀번호 변경 서비스 호출
            userService.verifyResetCode(uid, newpass);

            // 성공 응답
            return ResponseEntity.status(HttpStatus.OK).body("비밀번호가 성공적으로 변경되었습니다.");

        } else {
            // 실패 응답
            Map<String, Object> resultMap = new HashMap<>();
            resultMap.put("message", "인증번호가 일치하지 않거나 사용자 정보가 올바르지 않습니다.");
            return ResponseEntity.badRequest().body(resultMap);
        }
    }





}






