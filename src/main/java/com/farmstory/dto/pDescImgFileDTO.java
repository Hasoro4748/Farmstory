package com.farmstory.dto;

import com.farmstory.entity.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class pDescImgFileDTO {
    private int pfNo;
    private String p_sName1;
    private String p_sName2;
    private String p_sName3;
    private String rdate;

    private int pNo;

    // 외래키 컬럼
    private ProductDTO product;
}
