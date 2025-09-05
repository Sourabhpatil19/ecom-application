package com.app.ecom.dto;

import lombok.Data;

@Data
public class ProductRequest {
    private String name;
    private String description;
    private String category;
    private String price;
    private String image;
    private String stockQuantity;
}
