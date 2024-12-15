package com.farmstory.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderSummaryDTO {
    private int totalQty;
    private int totalPrice;
    private int totalDiscount;
    private int totalDelivery;
    private int totalPoint;
}
