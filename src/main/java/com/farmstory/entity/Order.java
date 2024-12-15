package com.farmstory.entity;

import com.farmstory.dto.CartDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderNo; // 주문 번호

    @CreationTimestamp
    private LocalDate orderDate;    //주문날짜
    private int totalPrice; // 주문 전체 금액
    private int totalQty;   //
    private String receipt;
    private int delivery;
    private String recHp;       // 주문 받는사람 전화번호
    private String recZip;      // 주문 받는 우편번호
    private String recAddr1;    // 주문 받는 주소1
    private String recAddr2;    // 주문 받는주소2
    private String payment;    // 결제 수단
    private String orderDesc; // 주문 부가 설명
    private int totalDiscount;
    private int usedPoint;
    private int earnPoint;

    // 외래키 컬럼
    private String uid;

    // 추가컬럼
    @Transient
    private String pName;
    @Transient
    private int price;
    /*
    @Transient
    private int itemQty;
    */
    @Transient
    private String name;
    @Transient
    private String p_sName1;

}
