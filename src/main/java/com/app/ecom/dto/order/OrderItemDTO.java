package com.app.ecom.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderItemDTO {
    private Long orderId;
    private Long productId;
    private int quantity;
    private BigDecimal price;


}
