package com.farmstory.controller.admin;

import com.farmstory.dto.*;
import com.farmstory.entity.Product;
import com.farmstory.service.CategoryService;
import com.farmstory.service.ProductService;
import com.farmstory.service.pDescImgFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Controller
public class ProductController {

    private final pDescImgFileService descImgFileService;
    private final ProductService productService;
    private final CategoryService categoryService;

    @PostMapping("/admin/register")
    public String register(ProductDTO productDTO, Model model) {

        log.info("register product : "  + productDTO.toString());

        // 파일 업로드
        pDescImgFileDTO uploadFile = descImgFileService.uploadpDescImgFile(productDTO);
        log.info("uploadeFIle : " + uploadFile.toString());

        // 상품 저장
        int PNo = productService.insertProduct(productDTO);

        log.info("nono" + PNo);



        // 이미지 저장 (이미지 파일 1개기 때문에 그냥 바로 저장 처리)
        uploadFile.setPNo(PNo);
        descImgFileService.insertpDescImgFile(uploadFile);


        log.info("uploadFile" + uploadFile);

        CateDTO cate= categoryService.selectCategory("admin","main");

        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);

        return "redirect:/admin/main";
    }

    @GetMapping("/admin/product")
    public String product (Model model, PageRequestDTO pageRequestDTO){

        log.info("컨ㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴㄴ트ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ롤로ㅗㅗㅗㅗㅗㅗㅗㅗㅗㅗ러ㅓㅓㅓㅓㅓㅓㅓㅓㅓㅓ");
        ProductPageResponseDTO productPageResponseDTO = productService.getAllProductsWithAllInfo(pageRequestDTO);

        log.info("productPageResponseDTO : " + productPageResponseDTO.toString());

        CateDTO cate= categoryService.selectCategory("admin","product");

        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);

        model.addAttribute("productPageResponseDTO", productPageResponseDTO);

        return "admin_index";
    }

}
