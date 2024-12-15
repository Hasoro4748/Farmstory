package com.farmstory.controller.admin;

import com.farmstory.dto.*;
import com.farmstory.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Log4j2
@Controller
public class admin_ListController {

    private final UserService userService;
    private final pDescImgFileService descImgFileService;
    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderService orderService;

    @GetMapping("/admin/main")
    public String list (Model model, PageRequestDTO pageRequestDTO){

        log.info("list admin");

        OrderPageResponseDTO orderPageResponseDTO = orderService.getAllOrder(pageRequestDTO);
        ProductPageResponseDTO productPageResponseDTO = productService.getAllProductsWithAllInfo(pageRequestDTO);
        UserPageResponseDTO userPageResponseDTO = userService.selectUserAll(pageRequestDTO);


        log.info("productPageResponseDTO : " + productPageResponseDTO.toString());
        log.info("userPageResponseDTO : " + userPageResponseDTO.toString());
        log.info("orderPageResponseDTO : " + orderPageResponseDTO.toString());

        CateDTO cate= categoryService.selectCategory("admin","main");

        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);

        model.addAttribute("orderPageResponseDTO", orderPageResponseDTO);
        model.addAttribute("productPageResponseDTO", productPageResponseDTO);
        model.addAttribute("userPageResponseDTO", userPageResponseDTO);

        return "admin_index";
    }
}
