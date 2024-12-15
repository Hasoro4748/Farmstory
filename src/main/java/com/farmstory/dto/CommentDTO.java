package com.farmstory.dto;


import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private int no;
    private int parent;
    private String content;

    private String writer;
    private String regip;
    private String date;
    private String CsParent;


    //추가필드
    private String nick;

    private UserDTO user;

    public String getSubStringRdate() {
        if(date ==null){
            return date;
        }else{
            date =  date.substring(0,10);
            return date;
        }
    }



}
