package com.farmstory.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="pDescImgFile")
public class pDescImgFile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int pfNo;

    //oName 제거 컬럼 이름으로 이미지 구분
    private String p_sName1;
    private String p_sName2;
    private String p_sName3;

    @CreationTimestamp
    private String rdate;

    // 외래키 컬럼
    private int pNo;
}
