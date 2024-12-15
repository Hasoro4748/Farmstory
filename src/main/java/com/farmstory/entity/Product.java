package com.farmstory.entity;

import com.farmstory.dto.pDescImgFileDTO;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pNo;
    private String pName;
    private int price;
    private int stock;
    private int point;
    private int discount;
    private int delivery;

    @CreationTimestamp
    private LocalDate rdate;
    private String pDesc;

    private int prodCateNo;

    // 추가 필드
    @Transient
    private String p_sName1;
    @Transient
    private String p_sName2;
    @Transient
    private String p_sName3;

    @Transient
    private prodCate prodCate;

/*
    @ManyToOne
    @ToString.Exclude
    private pDescImgFile pDescImgFile;
*/
}
