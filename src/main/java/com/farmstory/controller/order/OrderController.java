package com.farmstory.controller.order;

import com.farmstory.dto.*;
import com.farmstory.service.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RequiredArgsConstructor
@Controller
@RequestMapping("/market")
public class OrderController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final UserService userService;
    private final CartService cartService;
    private final OrderService orderService;

    @PostMapping("/addOrder")
    public String addOrder(OrderDTO orderDTO,
                           @RequestParam("cartDTOList") String cartDTOListJson,Model model) throws IOException {
        log.info("컨트롤러에 ㄴ들어오냐?");
        log.info("Received cartDTOListJson: " + cartDTOListJson);
        ObjectMapper objectMapper = new ObjectMapper();
;
        List<CartDTO> cartDTOList = objectMapper.readValue(cartDTOListJson, new TypeReference<List<CartDTO>>() {});


        log.info("cartDTOLIST 오냐?? " + cartDTOList);

        OrderDTO savedOrder = orderService.insertOrder(orderDTO);

        if(savedOrder != null) {
            // cart 개수만큼 orderitem 추가
            for(int i=0; i<cartDTOList.size(); i++) {
                OrderItemDTO orderItemDTO = new OrderItemDTO();
                orderItemDTO.setOrderNo(savedOrder.getOrderNo());
                CartDTO cartDTO = cartDTOList.get(i);
                orderItemDTO.setDiscount(cartDTO.getTotalprice()-cartDTO.getDelprice());
                orderItemDTO.setItemQty(cartDTO.getCartProdQty());
                orderItemDTO.setPNo(cartDTO.getProdNo());
                orderItemDTO.setItemPrice(cartDTO.getTotalprice());

                orderService.insertOrderItem(orderItemDTO);
                productService.updateProductStock(orderItemDTO.getPNo(),orderItemDTO.getItemQty());
                userService.UpdateUserPoint(orderDTO.getUid(),orderDTO.getUsedPoint(),orderDTO.getEarnPoint());
                cartService.deleteCartByCartNo(cartDTO.getCartNo());
            }

        }

      log.info("afdssssssssssssssssssss" + orderDTO.toString());

        return "index";
    }

    @PostMapping("/orderlist")
    public String orderlist(CartDTO cartDTO ,Model model) throws JsonProcessingException {

        log.info("컨트롤러 들어옹ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ니/.?");

        log.info("카트dtod " + cartDTO);

        //uid set (시간 남으면 좋으코드로 develop)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (authentication != null && authentication.getPrincipal() instanceof UserDetails)
                ? ((UserDetails) authentication.getPrincipal()).getUsername()
                : null;

        log.info("현재 로그인한 사용자 uid: " + uid);

        cartDTO.setUid(uid);

        // 해당 product 들고오기
        ProductDTO productDTO = productService.selectProductById(cartDTO.getProdNo());
        cartDTO.setProductDTO(productDTO);

        // 해당 user 들고오기
        UserDTO userDTO = userService.selectUser(cartDTO.getUid());

        CateDTO cate= categoryService.selectCategory("market","order");

        List<CartDTO> cartDTOList = new ArrayList<>();
        cartDTOList.add(cartDTO);

        // 계산 먼저 처리하고 order에서 그대로 쓰기
        OrderSummaryDTO orderSummary = new OrderSummaryDTO();

        int totalQty = 0;
        int totalPrice = 0;
        int totalDiscount = 0;
        int totalDelivery = 0;
        int totalPoint = 0;

        for (CartDTO cart : cartDTOList) {
            totalQty += cart.getCartProdQty();
            totalPrice += cart.getTotalprice();
            totalDelivery += cart.getCart_delivery();
            totalPoint += cart.getProductDTO().getPoint() * cart.getCartProdQty();

            // 할인 금액 계산
            int discount = (cart.getProductDTO().getPrice() * cart.getCartProdQty()) - cartDTO.getTotalprice();
            totalDiscount += discount;
        }

        // 결과 DTO에 값 설정
        orderSummary.setTotalQty(totalQty);
        orderSummary.setTotalPrice(totalPrice);
        orderSummary.setTotalDiscount(totalDiscount);
        orderSummary.setTotalDelivery(totalDelivery);
        orderSummary.setTotalPoint(totalPoint);

        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        responseOrderDTO.setUserDTO(userDTO);
        responseOrderDTO.setCartDTOList(cartDTOList);

        // ObjectMapper를 사용해 cartDTOList를 JSON으로 직렬화
        ObjectMapper objectMapper = new ObjectMapper();
        String cartJson = objectMapper.writeValueAsString(cartDTOList);

        // Model에 cartJson 추가
        model.addAttribute("cartJson", cartJson);

        // 조회한 카테고리 정보를 모델에 추가
        model.addAttribute("cate", cate);
        model.addAttribute("responseOrderDTO", responseOrderDTO);
        model.addAttribute("orderSummary", orderSummary);

        log.info("responseOrderDTO"+ responseOrderDTO.toString());
        return "boardIndex";
    }

    @PostMapping("/CartToOrder")
    public String processSelectedCartItems(@RequestParam("selectedItems") String selectedItems, Model model) throws JsonProcessingException {
        // JSON 문자열을 List<Integer>로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        List<Integer> cartNos = objectMapper.readValue(selectedItems, new TypeReference<List<Integer>>() {});
        log.info("cartNos : "+cartNos);
        // cartNo별로 처리 로직 수행
        List<CartDTO> cartDTOList = new ArrayList<>();
        for (Integer cartNo : cartNos) {
            log.info("cartNo : "+cartNo);
            CartDTO cartDTO = cartService.selectCartByCartNo(cartNo);
            ProductDTO productDTO = productService.selectProductById(cartDTO.getProdNo());
            cartDTO.setProductDTO(productDTO);
            cartDTOList.add(cartDTO);
        }
        // 해당 user 들고오기
        UserDTO userDTO = userService.selectUser(cartDTOList.get(0).getUid());

        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        responseOrderDTO.setUserDTO(userDTO);
        responseOrderDTO.setCartDTOList(cartDTOList);

        // 계산 먼저 처리하고 order에서 그대로 쓰기
        OrderSummaryDTO orderSummary = new OrderSummaryDTO();

        int totalQty = 0;
        int totalPrice = 0;
        int totalDiscount = 0;
        int totalDelivery = 0;
        int totalPoint = 0;

        for (CartDTO cart : cartDTOList) {
            totalQty += cart.getCartProdQty();
            totalPrice += cart.getTotalprice();
            totalDelivery += cart.getCart_delivery();
            totalPoint += cart.getProductDTO().getPoint() * cart.getCartProdQty();

            // 할인 금액 계산
            int discount = (cart.getProductDTO().getPrice() * cart.getCartProdQty()) - cart.getTotalprice();
            totalDiscount += discount;
        }

        // 결과 DTO에 값 설정
        orderSummary.setTotalQty(totalQty);
        orderSummary.setTotalPrice(totalPrice);
        orderSummary.setTotalDiscount(totalDiscount);
        orderSummary.setTotalDelivery(totalDelivery);
        orderSummary.setTotalPoint(totalPoint);

        log.info("!!!!!!!!!!!!!!!cartDTOList : "+cartDTOList);
        CateDTO cate= categoryService.selectCategory("market","order");

        // ObjectMapper를 사용해 cartDTOList를 JSON으로 직렬화
        String cartJson = objectMapper.writeValueAsString(cartDTOList);

        // Model에 cartJson 추가
        model.addAttribute("cartJson", cartJson);


        model.addAttribute("orderSummary", orderSummary);
        model.addAttribute("cate", cate);
        model.addAttribute("responseOrderDTO", responseOrderDTO);
        log.info("responseOrderDTO"+ responseOrderDTO.toString());
        // 처리 후 페이지 리다이렉트
        return "boardIndex";
    }



}
