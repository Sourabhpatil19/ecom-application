package com.app.ecom.controller;

import com.app.ecom.dto.cart.CartItemRequest;
import com.app.ecom.dto.cart.CartItemResponse;
import com.app.ecom.service.CartItemServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartItemController {

    private final CartItemServices cartItemServices;
    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader ("X-User-ID") Long userId, @RequestBody CartItemRequest cartItemRequest) {
        if (!cartItemServices.addToCart(userId,cartItemRequest)) {
            return ResponseEntity.badRequest().body("Product out of Stock Or User Not Found");
        }
       return ResponseEntity.status(HttpStatus.CREATED).body("Product added successfully");

    }
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<String> removeFromCart(@RequestHeader ("X-User-ID") String userId,@PathVariable Long productId ) {
      if(!cartItemServices.deleteItemFromCart(userId,productId)){
          return ResponseEntity.badRequest().body("Product not found");
      }
      return ResponseEntity.status(HttpStatus.OK).body("Product removed successfully");
    }

    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getAllCartItems(@RequestHeader ("X-User-ID") String userId) {

        return new ResponseEntity<>(cartItemServices.fetchAllCartItem(userId),HttpStatus.OK);

    }
}
