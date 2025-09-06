package com.app.ecom.dto.product;

import lombok.Data;

@Data
public class ProductResponse {
    private Long id;
    private String name;
    private String description;
    private String category;
    private String price;
    private String image;
    private String stockQuantity;
    private Boolean active;
}
