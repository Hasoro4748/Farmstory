package com.farmstory.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductPageResponseDTO {

    private List<ProductDTO> productList;

    private String cate;
    private String type;
    private int pg;
    private int size;
    private int total;
    private int startNo;
    private int start, end;
    private boolean prev, next;

    @Builder
    public ProductPageResponseDTO(PageRequestDTO pageRequestDTO , List<ProductDTO> productList, int total) {
        this.cate = pageRequestDTO.getCate();
        this.type = pageRequestDTO.getType();
        this.pg = pageRequestDTO.getPg();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.productList = productList;

        this.startNo = total - ((pg - 1) * size);
        this.end = (int) (Math.ceil(this.pg / 10.0)) * 10;
        this.start = this.end - 9;

        int last  = (int) (Math.ceil(total / (double) size));

        this.end = end > last ? last : end;
        // end가 start보다 작을 때 end가 0 되는것 방지
        if(this.start > this.end) {
            this.end = this.start;
        }
        this.prev = this.start > 1;
        this.next = total > this.end * this.size;

    }


}
