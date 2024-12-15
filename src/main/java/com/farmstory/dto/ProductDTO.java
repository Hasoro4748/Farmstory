package com.farmstory.dto;

import com.farmstory.entity.prodCate;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Transient;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)  // null 값은 JSON 직렬화에서 제외
public class ProductDTO {
    private int pNo;
    private String pName;
    private int price;
    private int stock;
    private int point;
    private int discount;
    private int delivery;
    private String rdate;
    private String pDesc;

    // 업로드 전용
    private MultipartFile files1;
    private MultipartFile files2;
    private MultipartFile files3;

    private String p_sName1;
    private String p_sName2;
    private String p_sName3;

    private List<pDescImgFileDTO> pDescImgFile;

    // 외래키 컬럼
    private prodCateDTO prodCate;

    private int prodCateNo;

    // MultipartFile들을 리스트로 묶어 반환
    public List<MultipartFile> getMultipartFiles(){
        List<MultipartFile> files = new ArrayList<>();
        if (files1 != null) files.add(files1);
        if (files2 != null) files.add(files2);
        if (files3 != null) files.add(files3);
        return files;
    }

}
