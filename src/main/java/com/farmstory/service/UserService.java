package com.farmstory.service;

import com.farmstory.dto.*;
import com.farmstory.entity.Terms;
import com.farmstory.entity.User;
import com.farmstory.repository.Termsrepository;
import com.farmstory.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;


@Log4j2
@Service
@RequiredArgsConstructor
@RequestMapping("/api/contents/user")
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final Termsrepository termsRepository;
    private final ModelMapper modelMapper;

    private final EmailService emailService;
    private final JavaMailSender javaMailSender;
    private final HttpSession session;


    // order 후 point 업데이트
    public void UpdateUserPoint(String uid , int usedpoint, int earnpoint){
        userRepository.findById(uid).ifPresent(user -> {
            user.setPoint(user.getPoint()+earnpoint-usedpoint);
            userRepository.save(user);
        });

    }

    // 페이지네이션 User selectAll
    public UserPageResponseDTO selectUserAll(PageRequestDTO pageRequestDTO) {

        log.info("서비스 들어갔나여????????????????????????????????????? ");
        Pageable pageable = pageRequestDTO.getPageable("uid", 10);

        Page<User> users = userRepository.selectUserAllForList(pageRequestDTO, pageable);
        log.info(users.toString());

        List<UserDTO> userDTOS = users.getContent().stream().map(user -> {
            UserDTO userDTO = modelMapper.map(user, UserDTO.class);
            return userDTO;
        }).toList();
        System.out.println("asdfffffffffffffffffffffffffffffffffffff" + userDTOS);

        int total = (int) users.getTotalElements();

        log.info("total dddddddddddd" + total);
        return UserPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .userList(userDTOS)
                .total(total)
                .build();
    }


    public String loginUser(String uid, String password) {
        String endcodedpassword = passwordEncoder.encode(password);

        Optional<User> opt = userRepository.findByUidAndPass(uid, endcodedpassword);

        if (opt.isPresent()) {
            User user = opt.get();
            return user.getUid();
        }

        return null;

    }

    public UserDTO insertUser(UserDTO userDTO) {
        String encodepass = passwordEncoder.encode(userDTO.getPass());
        userDTO.setPass(encodepass);

        User user = modelMapper.map(userDTO, User.class);

        User saveUser = userRepository.save(user);

        return modelMapper.map(saveUser, UserDTO.class);
    }

    public int selectCountUserByType(String type, String value) {
        log.info("value : " + value);
        int count = 0;
        if (type.equals("uid")) {
            count = userRepository.countByUid(value);

        } else if (type.equals("nick")) {
            count = userRepository.countByNick(value);

        } else if (type.equals("email")) {
            count = userRepository.countByEmail(value);

        } else if (type.equals("hp")) {
            count = userRepository.countByHp(value);
        }
        log.info("count : " + count);

        return count;

    }

    public void selectUser() {
    }

    public void selectUsers() {
    }

    public void updateUser() {
    }

    public void deleteUser() {
    }


    //terms
    public TermsDTO selectTemrs() {

        List<Terms> termsList = termsRepository.findAll();
        return termsList.get(0).toDTO();


    }


    // 아이디 찾기 서비스 추가
    public void receiveCode(String name, String email) {

        // 1. 이름과 이메일로 DB에서 유저 검색
        Optional<User> user = userRepository.findByNameAndEmail(name, email);

        if (user.isEmpty()) {
            throw new RuntimeException("해당 이름과 이메일로 계정을 찾을 수 없습니다.");
        }

        // 2. 이메일 서비스에서 코드 생성 및 이메일 전송 (세션에 코드 저장)
        String verificationCode = emailService.sendMail(email, "contents/user/email", session);


        // 3. 인증번호를 세션에 저장
        session.setAttribute("code", verificationCode);  // 세션에 인증번호 저장
        session.setAttribute("name", name);  // 세션에 사용자 이름 저장
        session.setAttribute("email", email);  // 세션에 사용자 이메일 저장


    }

    // 인증번호 검증 및 아이디 반환
    public User verifyCodeForUser(String verificationCode, String name, String email) {
        // 1. 세션에서 저장된 인증번호 및 사용자 정보 가져오기
        String sessionCode = (String) session.getAttribute("code");  // 세션에 저장된 인증번호 가져오기
        String sessionName = (String) session.getAttribute("name");
        String sessionEmail = (String) session.getAttribute("email");

        // 2. 검증: 세션에 저장된 인증번호와 사용자 정보가 입력된 값과 일치하는지 확인
        if (sessionCode == null || !sessionCode.equals(verificationCode)
                || !sessionName.equals(name) || !sessionEmail.equals(email)) {
            throw new RuntimeException("인증번호가 일치하지 않거나 사용자 정보가 일치하지 않습니다.");
        }

        // 3. 검증 성공 후, 유저의 아이디 반환
        User user = userRepository.findByNameAndEmail(name, email)
                .orElseThrow(() -> new RuntimeException("해당 이름과 이메일로 계정을 찾을 수 없습니다."));

        return user;  // 유저의 아이디 반환
    }

    // 아이디로 회원 엔티티 불러오기
    public UserDTO selectUser(String uid) {
        Optional<User> optUser = userRepository.findById(uid);
        if (optUser.isPresent()) {
            User user = optUser.get();
            log.info("usudusdfusfudsufusudfuswu" + user);

            UserDTO dto = modelMapper.map(user, UserDTO.class);
            log.info("asfddddddddddddd" + dto.toString());

            return modelMapper.map(user, UserDTO.class);
        }
        return null;
    }


    public UserDTO modifyUser(UserDTO userDTO) {
        Boolean result = userRepository.existsById(userDTO.getUid());

        if (result) {
            User user = modelMapper.map(userDTO, User.class);
            user.setRegDate(LocalDateTime.parse(userDTO.getRegDate()));
            user.setName("탈퇴회원");
            log.info("이유저인가>?" + user);
            User savedUser = userRepository.save(user);
            UserDTO resultUser = modelMapper.map(savedUser, UserDTO.class);
            return resultUser;
        }
        return null;
    }


    // 비밀번호 찾기 서비스 추가
    public String resetCode(String uid, String email) {

        // 1. 이름과 이메일로 DB에서 유저 검색
        Optional<User> user = userRepository.findByUidAndEmail(uid, email);

        if (user.isEmpty()) {
            throw new RuntimeException("해당 이름과 이메일로 계정을 찾을 수 없습니다.");
        }

        // 2. 이메일 서비스에서 코드 생성 및 이메일 전송 (세션에 코드 저장)
        String verificationCode = emailService.sendMail(email, "contents/user/email", session);


        // 3. 인증번호를 세션에 저장
        session.setAttribute("code", verificationCode);  // 세션에 인증번호 저장
        session.setAttribute("uid", uid);  // 세션에 사용자 아이디 저장
        session.setAttribute("email", email);  // 세션에 사용자 이메일 저장


        return verificationCode;
    }


    // 인증번호 검증 및 비밀번호 변경
    public User verifyResetCode(String uid, String newpass) {
        // 1. 사용자가 존재하는지 확인 (UID와 Email로 조회)
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("해당 사용자 정보를 찾을 수 없습니다."));


        // 3. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(newpass);


        // 4. 유저의 비밀번호를 암호화된 비밀번호로 업데이트
        user.setPass(encodedPassword);
        userRepository.save(user);  // 비밀번호 저장

        // 5. 비밀번호 변경 완료 후, 유저 정보 반환 (필요한 경우)
        return user;
    }


    // 나의 정보


    // 별명, 주소 업데이트 메서드
    public void updateUserInfo(UserDTO userDTO) {
        System.out.println("userDTO = " + userDTO);
        User user = userRepository.findById(userDTO.getUid())
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
        if (userDTO.getNick() != null) {
            user.setNick(userDTO.getNick());
        }
        if (userDTO.getZip() != null) {
            user.setZip(userDTO.getZip());
        }
        if (userDTO.getAddr1() != null) {
            user.setAddr1(userDTO.getAddr1());
        }
        if (userDTO.getAddr2() != null) {
            user.setAddr2(userDTO.getAddr2());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getHp() != null) {
            user.setHp(userDTO.getHp());
        }
        userRepository.save(user);
    }


    public boolean isNickDuplicate(String nick) {
        return userRepository.countByNick(nick) > 0;
    }


    public User modifyPassword(String uid, String newPass) {
        // 1. 사용자가 존재하는지 확인
        User user = userRepository.findById(uid)
                .orElseThrow(() -> new RuntimeException("해당 사용자를 찾을 수 없습니다."));

        // 2. 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(newPass);

        // 3. 암호화된 비밀번호로 업데이트
        user.setPass(encodedPassword);
        userRepository.save(user);

        return user;
    }


}

