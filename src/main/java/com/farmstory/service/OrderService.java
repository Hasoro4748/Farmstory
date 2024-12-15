package com.farmstory.service;

import com.farmstory.dto.*;
import com.farmstory.entity.*;
import com.farmstory.repository.OrderItemRepository;
import com.farmstory.repository.OrderRepository;
import com.querydsl.core.Tuple;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;
    private final OrderItemRepository orderItemRepository;

    public void deleteOrder(int orderNo){
        orderRepository.deleteById(orderNo);
    }

    public OrderDTO insertOrder(OrderDTO orderDTO) {

        Order order = modelMapper.map(orderDTO, Order.class);
        Order savedOrder = orderRepository.save(order);

        OrderDTO savedorderDTO = modelMapper.map(savedOrder, OrderDTO.class);

        return savedorderDTO;
    }

    public OrderItemDTO insertOrderItem(OrderItemDTO orderItemDTO) {
        OrderItem orderItem = modelMapper.map(orderItemDTO, OrderItem.class);
        OrderItem savedOrderItem = orderItemRepository.save(orderItem);

        OrderItemDTO savedorderItemDTO = modelMapper.map(savedOrderItem, OrderItemDTO.class);

        return savedorderItemDTO;
    }

    public OrderPageResponseDTO getMyOrder(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("orderNo" , 10);

        Page<Tuple> orders = null;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String uid = (authentication != null && authentication.getPrincipal() instanceof UserDetails)
                ? ((UserDetails) authentication.getPrincipal()).getUsername()
                : null;

        log.info("현재 로그인한 사용자 uid: " + uid);

        pageRequestDTO.setUid(uid);

        orders = orderRepository.selectOrderMyForList(pageRequestDTO, pageable);
        log.info("너까지 되니? " + orders);


        List<OrderDTO> orderList = orders.getContent().stream().map(tuple -> {
            Order order = tuple.get(0, Order.class);
            String name = (tuple.get(1, String.class));
            Product product = tuple.get(2, Product.class);
            pDescImgFile pDescImgFile = tuple.get(3, pDescImgFile.class);

            order.setP_sName1(pDescImgFile.getP_sName1());
            order.setPName(product.getPName());
            order.setPrice(product.getPrice());
            order.setName(name);

            return modelMapper.map(order, OrderDTO.class);
        }).toList();

        log.info("매핑 되니? " + orderList);

        int total = (int) orders.getTotalElements();

        return OrderPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .orderDTOList(orderList)
                .total(total)
                .build();
    }

    public OrderPageResponseDTO getAllOrder(PageRequestDTO pageRequestDTO) {

        Pageable pageable = pageRequestDTO.getPageable("orderNo" , 10);

        Page<Tuple> orders = null;
        log.info("너는 되니? " + orders);


        orders = orderRepository.selectOrderAllForList(pageRequestDTO, pageable);
        log.info("너까지 되니? " + orders);


        List<OrderDTO> orderList = orders.getContent().stream().map(tuple -> {
            Order order = tuple.get(0, Order.class);
            String name = (tuple.get(1, String.class));
            Product product = tuple.get(2, Product.class);

            if (product != null) {
                order.setPName(product.getPName());
                order.setPrice(product.getPrice());
            } else {
                order.setPName("asdfassfd");
                order.setPrice(0);
            }

            order.setName(name);

            return modelMapper.map(order, OrderDTO.class);
        }).toList();

        log.info("매핑 되니? " + orderList);

        int total = (int) orders.getTotalElements();

        return OrderPageResponseDTO.builder()
                .pageRequestDTO(pageRequestDTO)
                .orderDTOList(orderList)
                .total(total)
                .build();
    }

}
