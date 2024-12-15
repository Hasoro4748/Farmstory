package com.farmstory.dto;

import com.farmstory.entity.Article;
import com.farmstory.entity.Cate;
import jakarta.persistence.Transient;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArticleDTO {
    private int articleNo;   //board no  autoincrement
    private int cateNo; // croptalk={story,garden,re}, event = {event}, community={notice,today,cook,cs,faq}
    private String title;

    //제목
    private String content;  //내용
    private String writer;  //작성자
    private String date;   //작성일 now()
    private String regIp;   //작성자ip
    @Builder.Default
    private int file = 0;
    @Builder.Default
    private int hit =0;
    @Builder.Default
    private int com=0;

    @Builder.Default
    private boolean isNotice=false;

    private int noticeCate;
    private String updateDate;


    //추가필드
    private String nick;


    private String subStringRdate;
    //    private MultipartFile file1;
    private List<MultipartFile> files;

    private List<FileDTO> fileList;
    private List<CommentDTO> commentList;
    private int pg;

    public String getSubStringRdate() {
        if(date == null){
            return "";
        }else{
            subStringRdate =  date.substring(0,10);
            return subStringRdate;
        }
    }

    public Article toEntity(){
        return Article.builder()
                .articleNo(articleNo)
                .cateNo(cateNo)
                .title(title)
                .content(content)
                .writer(writer)
                .regIp(regIp)
                .file(file)
                .hit(hit)
                .com(com)
                .isNotice(isNotice)
                .noticeCate(noticeCate)
                .build();
    }

}
