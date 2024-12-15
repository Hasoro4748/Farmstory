package com.farmstory.dto;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private int orderNo; // 주문 번호
    private LocalDate orderDate;    //주문날짜
    private int totalPrice; // 주문 전체 금액
    private int totalQty;   //
    private String receipt;
    private int delivery;
    private String recHp;       // 주문 받는사람 전화번호
    private String recZip;      // 주문 받는 우편번호
    private String recAddr1;    // 주문 받는 주소1
    private String recAddr2;    // 주문 받는주소2
    private String payment;    // 결제금액?
    private String orderDesc; // 주문 부가 설명
    private int totalDiscount;
    private int usedPoint;
    private int earnPoint;

    // 외래키 컬럼
    private String uid;

    // 추가컬럼
    private String pName;
    private int price;
    private int itemQty;
    private String name;
    private String p_sName1;
}
