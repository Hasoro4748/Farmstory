package com.farmstory.dto;

import com.farmstory.entity.Product;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartDTO {
    private int cartNo;                 // 장바구니 번호
    private int cartProdQty;            // 장바구니 상품 갯수
    private LocalDate cartProdDate; // 장바구니 날짜

    //외래키 목록
    private String uid;    // 유저 아이디

    // 컬럼이름맞게 수정!!!
    private int prodNo; // 상품 번호

    private ProductDTO productDTO;

    // 준혁 추가필드
    // 할인율 적용된 price
    private int delprice;
    private int totalprice;
    private int cart_delivery;


}
