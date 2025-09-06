package com.app.ecom.dto.order;


import com.app.ecom.entites.order.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class OrderResponse {

    private Long id;
    private BigDecimal total;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;



}
