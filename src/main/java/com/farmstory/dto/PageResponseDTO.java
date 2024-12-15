package com.farmstory.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PageResponseDTO {

    private List<ArticleDTO> dtoList;
    private int cateNo;

    private int pg;
    private int total;
    private int size;
    private int startNo;
    private int start, end;
    private boolean prev,next;

    private int pageSize=10;


    private String type;
    private String keyword;


    @Builder
    public PageResponseDTO(PageRequestDTO pageRequestDTO,List<ArticleDTO> dtoList, int total){
        this.cateNo = pageRequestDTO.getCateNo();

        this.pg = pageRequestDTO.getPg();
        this.total = total;
        this.size = pageRequestDTO.getSize();
        this.dtoList = dtoList;
        this.startNo = total - ((pg-1)*size);  //개시물 시작번호
        this.end =(int) (Math.ceil(this.pg/5.0))*5;
        this.start = this.end -4;
        int last = (int)(Math.ceil(total/(double)size));
        this.end = end > last? last:end;
        this.prev = this.start>1;
        this.next = total > this.end *this.size;
        this.type = pageRequestDTO.getType();
        this.keyword = pageRequestDTO.getKeyword();
        // Ensure `end` is at least 1 if `start` is 1
        if (this.start == 1 && this.end == 0) {
            this.end = 0;
        }


    }


}
