package com.app.ecom.dto.cart;


import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemResponse {


    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
