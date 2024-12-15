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
@Table(name="cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartNo;                 // 장바구니 번호
    private int cartProdQty;            // 장바구니 상품 갯수
    @CreationTimestamp
    private LocalDateTime cartProdDate; // 장바구니 날짜

    //외래키 목록
    private String uid;    // 유저 아이디



    // 준혁 추가필드
    // 할인율 적용된 price
    private int prodNo; // 상품 번호
    private int delprice;
    private int totalprice;
    private int cart_delivery;

}
