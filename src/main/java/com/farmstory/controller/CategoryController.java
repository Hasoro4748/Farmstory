package com.farmstory.controller;

import com.farmstory.dto.CateDTO;
import com.farmstory.security.MyUserDetails;
import com.farmstory.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;




    @GetMapping("/{cateGroup}/{cateName}")
    public String handleCategory(@PathVariable("cateGroup") String group,
                                 @PathVariable("cateName") String cateName,
                                 @RequestParam(value = "content", required = false) String content,
                                 @AuthenticationPrincipal MyUserDetails userDetails,
                                 Model model){
        boolean isAdmin = false;
        if(userDetails != null){
             isAdmin = userDetails.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        }

        model.addAttribute("isAdmin", isAdmin);//

        // 카테고리 정보를 조회
        CateDTO cate = categoryService.selectCategory(group, cateName);

        log.info(cate);

        if(cate ==null){
            return "/error";
        }


        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);
        model.addAttribute("content", content);

        if(group.equals("admin")){
            return "admin_index";
        }

        return "boardIndex";


    }







}
