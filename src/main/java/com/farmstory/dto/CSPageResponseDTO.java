package com.farmstory.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CSPageResponseDTO {

    private List<CsArticleDTO> dtoList;
    private int cateNo = 504;
    private int pg;
    private int total;
    private int size;
    private int startNo;
    private int start, end;
    private boolean prev,next;

    private int pageSize=10;


    private String type;
    private String keyword;
    private String uid;



    @Builder
     public CSPageResponseDTO(CSPageRequestDTO  cspageRequestDTO, List<CsArticleDTO> csArticleDTOS, int total){
         this.cateNo = cspageRequestDTO.getCateNo();
         this.pg = cspageRequestDTO.getPg();
         this.total = total;
         this.size = cspageRequestDTO.getSize();
         this.dtoList = csArticleDTOS;
         this.startNo = total - ((pg-1)*size);  //개시물 시작번호
         this.end =(int) (Math.ceil(this.pg/5.0))*5;
         this.start = this.end -4;
         int last = (int)(Math.ceil(total/(double)size));
         this.end = end > last? last:end;
         this.prev = this.start>1;
         this.next = total > this.end *this.size;
         this.type = cspageRequestDTO.getType();
         this.keyword = cspageRequestDTO.getKeyword();
         this.uid= cspageRequestDTO.getUid();
        // Ensure `end` is at least 1 if `start` is 1
        if (this.start == 1 && this.end == 0) {
            this.end = 0;
        }


     }


}
