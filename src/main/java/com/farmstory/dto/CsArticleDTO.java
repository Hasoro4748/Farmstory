package com.farmstory.dto;

import jakarta.persistence.Transient;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CsArticleDTO {
    private String CsNo;
    @Builder.Default//board no  autoincrement
    private int cateNo=504; // croptalk={story,garden,re}, event = {event}, community={notice,today,cook,cs,faq}
    private String title;
    //제목
    private String content;  //내용
    private String writer;  //작성자
    private String createdate;   //작성일 now()
    private String updateDate;   //작성일 now()
    private String regIp;   //작성자ip
    @Builder.Default
    private int file = 0;
    @Builder.Default
    private int hit =0;
    @Builder.Default
    private int comment=0;
    private String email;


    @Builder.Default
    private boolean isCompleted=false;
    private int inquiry ;

    //추가필드
    @Transient
    private String nick;

    @Transient
    private String subStringRdate;
    //    private MultipartFile file1;
    private List<MultipartFile> files;

    private List<FileDTO> fileList;
    private List<CommentDTO> commentList;
    private int pgCsArticleDTO;

    public String getSubStringRdate(String date) {
        if(date ==null){
            return "";
        }else{
            return date.substring(0,10);
        }
    }



}
