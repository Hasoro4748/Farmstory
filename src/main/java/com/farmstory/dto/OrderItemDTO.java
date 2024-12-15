package com.farmstory.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemDTO {
    private int orderItemNo;            // 주문번호
    private LocalDateTime orderDate;    // 주문날짜
    private int itemPrice;
    private int itemQty;
    private int discount;

    // 외래키 목록
    private int orderNo;    //주문 번호
    private int pNo;        //상품 번호
}
