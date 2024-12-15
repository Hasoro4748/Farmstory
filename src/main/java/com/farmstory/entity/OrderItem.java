package com.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="orderItem")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderItemNo;            // 주문번호
    @CreationTimestamp
    private LocalDateTime orderDate;    // 주문날짜
    private int itemPrice;
    private int itemQty;

    // 할인 되는 금액
    private int discount;

    // 외래키 목록
    private int orderNo;    //주문 번호
    private int pNo;        //상품 번호

    //추가 컬럼
    @Transient
    private Product product;
}
