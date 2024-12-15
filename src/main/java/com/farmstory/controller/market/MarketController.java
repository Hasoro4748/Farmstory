package com.farmstory.controller.market;

import com.farmstory.dto.*;
import com.farmstory.entity.OrderItem;
import com.farmstory.entity.Product;
import com.farmstory.repository.CartRepository;
import com.farmstory.service.CartService;
import com.farmstory.service.CategoryService;
import com.farmstory.service.ProductService2;
import com.farmstory.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/market")
public class MarketController {

    private final CategoryService categoryService;
    private final ProductService2 productService;
    private final CartService cartService;
    private final UserService userService;
    @Value("${spring.servlet.multipart.location}")
    private String uploadedPath;

    @GetMapping("/plist")
    public String plist(@RequestParam(value = "content", required = false) String content,
                        @RequestParam(value ="page", defaultValue = "0") int page,
                        Model model, PageRequestDTO pageRequestDTO){
        CateDTO cate = categoryService.selectCategory("market","plist");
        int cateNo = cate.getCateNo();
        log.info("cate : "+cate);
        List<ArticleDTO> articles = null;

        ProductPageResponseDTO productPageResponseDTO = productService.getAllProductsWithAllInfo(pageRequestDTO);

        model.addAttribute("cate", cate);
        model.addAttribute("content", content);
        model.addAttribute("ProductPageResponseDTO", productPageResponseDTO);
        return "boardIndex";
    }

    @GetMapping("/pview/{pNo}")
    public String plist(@RequestParam(value = "content", required = false) String content
                        ,@PathVariable int pNo
                        ,Model model){
        CateDTO cate = categoryService.selectCategory("market","pview");
        log.info("cate : "+cate);

        ProductDTO productDTO = productService.selectProductById(pNo);
        log.info("productDTO : "+productDTO);
        model.addAttribute("cate", cate);
        model.addAttribute("content", content);
        model.addAttribute("productDTO", productDTO);
        return "boardIndex";
    }

/*
    @PostMapping("/cart")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cartInsert(@RequestParam(value = "content", required = false) String content
            , Model model
            , @RequestBody CartDTO cartDTO){
        Map<String, Object> response = new HashMap<>();
        log.info("!!!!!!!!cartInsert : "+cartDTO);
        try{
            ProductDTO product = productService.selectProductById(cartDTO.getProdNum());
            cartDTO.setProdNo(product);
            boolean success = cartService.insertCart(cartDTO);
            if(success){
                response.put("result", 1);
            }
            else{
                response.put("result", 0);
            }
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("result", 0);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
*/
    @PostMapping("/cart")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> cart1(@RequestBody CartDTO cartDTO, Model model){
        log.info("cart 들어왔나? : "+cartDTO.toString());
        Map<String, Object> response = new HashMap<>();
        try {
            boolean success = cartService.insertCart1(cartDTO);
            if (success) {
                response.put("result", 1);
            } else {
                response.put("result", 0);
            }
            return ResponseEntity.ok(response);
        }catch(Exception e){
            response.put("result", 0);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @GetMapping("/cart")
    public String cart(@RequestParam(value = "content", required = false) String content
            ,Model model){

        CateDTO cate = categoryService.selectCategory("market","cart");
        log.info("cate : "+cate);

        List<CartDTO> cartList = cartService.findCartWithUid();
        log.info("cartList : "+cartList);

        model.addAttribute("cate", cate);
        model.addAttribute("content", content);

        model.addAttribute("cartList", cartList);

        return "boardIndex";
    }



    @DeleteMapping("/CartDelete")
    public ResponseEntity<Map<String, Object>> cartDelete(@RequestBody List<CartDTO> selectedItems) {
        Map<String, Object> response = new HashMap<>();

        try {
            // JSON으로 받은 선택 항목 처리 로직
            for (CartDTO cart : selectedItems) {
                cartService.deleteCartByCartNo(cart.getCartNo());
            }
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 오류 발생 시 실패 응답
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/productDelete")
    public ResponseEntity<Map<String, Object>> productDelete(@RequestBody List<Integer> selectedItems) {
        Map<String, Object> response = new HashMap<>();

        log.info("productDelete : "+selectedItems);

        try {
            // JSON으로 받은 선택 항목 처리 로직
            for (Integer pNo : selectedItems) {
                productService.deleteProductById(pNo);
            }
            response.put("success", true);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // 오류 발생 시 실패 응답
            response.put("success", false);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
