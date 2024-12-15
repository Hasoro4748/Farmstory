package com.farmstory.controller;


import com.farmstory.dto.ArticleDTO;
import com.farmstory.dto.ProductDTO;
import com.farmstory.service.ArticleService;
import com.farmstory.service.ProductService;
import com.farmstory.service.ProductService2;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Log4j2
@Controller
public class MainController {

    private final ArticleService articleService;
    private final ProductService2 productService;

    //main page
    @GetMapping(value = {"/","/index","/category"})
    public String index(Model model) {

        //메인화면 게시글
        List<ArticleDTO> gardens  = articleService.mainArticle(302);
        List<ArticleDTO> schools  = articleService.mainArticle(303);
        List<ArticleDTO> storys  = articleService.mainArticle(301);
        List<ArticleDTO> notices  = articleService.mainNoticeArticle(501);

        log.info("garden: " + gardens);

        List<ProductDTO> products = productService.mainProduct();





        model.addAttribute("products", products);

        model.addAttribute("notices", notices);
        model.addAttribute("gardens", gardens);
        model.addAttribute("schools", schools);
        model.addAttribute("storys", storys);



        return "index";
    }



}
