package com.farmstory.controller.myinfo;

import com.farmstory.dto.CateDTO;
import com.farmstory.dto.OrderPageResponseDTO;
import com.farmstory.dto.PageRequestDTO;
import com.farmstory.service.CategoryService;
import com.farmstory.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/myInfo")
public class MyOrderController {

    private final OrderService orderService;
    private final CategoryService categoryService;

    @GetMapping("/orderitem")
    public String MyOrderlist(Model model ,PageRequestDTO pageRequestDTO){
        log.info("컨트롤러 들어옹ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ니/.?");

        log.info("dsfdsfs" + pageRequestDTO);
        OrderPageResponseDTO orderPageResponseDTO = orderService.getMyOrder(pageRequestDTO);

        log.info("myinfoorder"  + orderPageResponseDTO);

        CateDTO cate= categoryService.selectCategory("myinfo","orderitem");

        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);
        model.addAttribute("orderPageResponseDTO", orderPageResponseDTO);

        return "boardIndex";
    }
}
