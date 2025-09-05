package com.app.ecom.controller;


import com.app.ecom.dto.ProductRequest;
import com.app.ecom.dto.ProductResponse;
import com.app.ecom.service.ProductService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;
    @GetMapping
   public ResponseEntity<List<ProductResponse>> getAllProducts(){
       return ResponseEntity.ok(productService.fetchAllProducts());
   }

   @PostMapping
   public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest){
        productService.addProduct(productRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("Product created Successfully");
   }
   @PutMapping("/{id}")
   public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest) {
       if (productService.updateProduct(id, productRequest) == true) {
           return ResponseEntity.status(HttpStatus.CREATED).body("Product updated Successfully");
       } else {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found");
       }
   }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        if(productService.deleteProduct(id) == true) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Product deleted Successfully");
        }

        else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Product not found");
        }

    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> getProducts(@RequestParam String keyword){
        if(productService.searchProducts(keyword).isEmpty()) {

            List<ProductResponse> productResponseList = productService.fetchAllProducts();

            return ResponseEntity.ok(productResponseList);
        }else {
      return ResponseEntity.ok(productService.searchProducts(keyword));
    }}
}
