package com.farmstory.controller.admin;

import com.farmstory.dto.CateDTO;
import com.farmstory.dto.OrderPageResponseDTO;
import com.farmstory.dto.PageRequestDTO;
import com.farmstory.dto.ProductPageResponseDTO;
import com.farmstory.service.CategoryService;
import com.farmstory.service.OrderService;
import com.farmstory.service.ProductService;
import com.farmstory.service.pDescImgFileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Log4j2
@Controller
public class admin_OrderController {

    private final CategoryService categoryService;
    private final OrderService orderService;

    @GetMapping("/admin/a_order")
    public String product (Model model, PageRequestDTO pageRequestDTO){

        OrderPageResponseDTO orderPageResponseDTO = orderService.getAllOrder(pageRequestDTO);

        log.info("adfasfasfa"  + orderPageResponseDTO);

        CateDTO cate= categoryService.selectCategory("admin","a_order");

        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);
        model.addAttribute("orderPageResponseDTO", orderPageResponseDTO);

        return "admin_index";
    }
    @DeleteMapping("/admin/orderDelete")
    public ResponseEntity<Map<String, Object>> orderDelete(@RequestBody List<Integer> selectedItems) {
        Map<String, Object> response = new HashMap<>();

        log.info("oderDelete : "+selectedItems);

        try {
            // JSON으로 받은 선택 항목 처리 로직
            for (Integer orderNo : selectedItems) {
                orderService.deleteOrder(orderNo);
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
