package com.farmstory.controller.admin;

import com.farmstory.dto.UserDTO;
import com.farmstory.entity.User;
import com.farmstory.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RequiredArgsConstructor
@Log4j2
@RestController
public class admin_UserController {

    private final UserService userService;

    @PostMapping("/admin/findUser")
    public ResponseEntity<UserDTO> getUser(@RequestBody Map<String, String> payload) {
        log.info("들어가니~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        String uid = payload.get("uid");

        log.info("UID 요청: " + uid);
        UserDTO user = userService.selectUser(uid);
        log.info("사용자 정보: " + user);

        if (user != null) {
            return ResponseEntity.ok(user); // 200 OK와 함께 사용자 정보 반환
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PostMapping("/admin/modifyRole")
    public ResponseEntity<UserDTO> modifyRole(@RequestBody Map<String, String> payload) {
        String uid = payload.get("uid");

        UserDTO user = userService.selectUser(uid);

        if(payload.get("role").equals("LEAVE")) {
            // 탈퇴할 회원일시 id, regdate, leavedate, role 빼고 생성자로 null 처리
            user = new UserDTO(uid, user.getRegDate(), user.getLeaveDate(), user.getRole());
            user.setLeaveDate(String.valueOf(LocalDateTime.now()));
        }

        user.setRole(payload.get("role"));

        UserDTO resultUser = userService.modifyUser(user);

        if (resultUser != null) {
            return ResponseEntity.ok(resultUser); // 200 OK와 함께 사용자 정보 반환
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }

    @PostMapping("/admin/quitUser")
    public ResponseEntity<UserDTO> quitUser(@RequestBody Map<String, String> payload) {
        String uid = payload.get("uid");

        UserDTO user = userService.selectUser(uid);
        UserDTO quituser = new UserDTO();

        quituser.setUid(uid);
        quituser.setRegDate(user.getRegDate());
        quituser.setLeaveDate(String.valueOf(LocalDateTime.now()));

        UserDTO resultUser = userService.modifyUser(quituser);

        if (resultUser != null) {
            return ResponseEntity.ok(resultUser); // 200 OK와 함께 사용자 정보 반환
        } else {
            return ResponseEntity.notFound().build(); // 404 Not Found
        }
    }
}