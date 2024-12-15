package com.farmstory.dto;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private String uid;
    private String pass;
    private String name;
    private String nick;
    private String email;
    private String hp;
    private int point;

    @Builder.Default
    private String role="USER";
    private String zip;
    private String addr1;
    private String addr2;
    private String regip;
    private String regDate;
    private String leaveDate;


    // 탈퇴 회원을 위한 생성자
    public UserDTO(String uid, String regDate, String leaveDate, String role) {
        this.uid = uid;
        this.regDate = regDate;
        this.leaveDate = leaveDate;
        this.role = role;
        this.name = null;
        this.email = null;
        this.pass = null;
        this.nick = null;
        this.hp = null;
        this.point = 0;
        this.zip = null;
        this.addr1 = null;
        this.addr2 = null;
        this.regip = null;
    }

}
