package com.app.ecom.service;

import com.app.ecom.dto.cart.CartItemRequest;
import com.app.ecom.dto.cart.CartItemResponse;
import com.app.ecom.entites.order.CartItem;
import com.app.ecom.entites.product.Product;
import com.app.ecom.entites.user.User;
import com.app.ecom.repository.CartItemRepository;
import com.app.ecom.repository.ProductRepository;
import com.app.ecom.repository.UserRepository;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@JsonSerialize
public class CartItemServices {
private final CartItemRepository cartItemRepository;
private final ProductRepository productRepository;

    private final UserRepository userRepository;



    public Boolean addToCart(Long userId, CartItemRequest cartItemRequest) {
        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
        if (productOpt.isEmpty()) {

            return false;
        }
        Product product = productOpt.get();
        if (product.getStockQuantity() < cartItemRequest.getQuantity()) {
            return false ;
        }
        Optional<User>userOpt =userRepository.findById(userId);
        if (userOpt.isEmpty()) {
            return false;
        }
        User user=userOpt.get();

        CartItem existingCartItem =cartItemRepository.findByUserAndProduct(user,product);
        if (existingCartItem != null) {
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(existingCartItem.getPrice().multiply(new BigDecimal(cartItemRequest.getQuantity())));
            cartItemRepository.save(existingCartItem);
        }else{
        CartItem cartItem=new CartItem();

            cartItem.setProduct(product);
            cartItem.setUser(user);
            cartItem.setQuantity(cartItemRequest.getQuantity());
           cartItem.setPrice(product.getPrice().multiply(new BigDecimal(cartItemRequest.getQuantity())));

            cartItemRepository.save(cartItem);
    }
        return true;
    }

    public Boolean deleteItemFromCart(String userId, Long productId) {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty()) {

            return false;
        }
        Optional<User>userOpt =userRepository.findById(Long.valueOf(userId));
        if (userOpt.isEmpty()) {
            return false;
        }
      if (productOpt.isPresent() && userOpt.isPresent()) {
          cartItemRepository.deleteByUserAndProduct(userOpt.get(),productOpt.get());
          return true;
      }
        return false;
    }

    public List<CartItemResponse> fetchAllCartItem(String userId) {
           List <CartItem> cartItem= userRepository.findById(Long.valueOf(userId)).
                    map(cartItemRepository::findByUser).orElseGet(List::of);


        // 3. Map to response DTOs (manually or via ModelMapper)
        return cartItem.stream()
                .map(this::mapToCartItemResponse)
                .toList();




    }
    public List<CartItem> fetchAllCartItems(String userId) {
        List <CartItem> cartItem= userRepository.findById(Long.valueOf(userId)).
                map(cartItemRepository::findByUser).orElseGet(List::of);



        return cartItem;}




    private CartItemResponse mapToCartItemResponse(CartItem cartItem) {
        CartItemResponse response = new CartItemResponse();

        response.setProductName(cartItem.getProduct().getName());
        response.setQuantity(cartItem.getQuantity());
        response.setPrice(BigDecimal.valueOf(cartItem.getPrice().doubleValue()));


        return response;
    }

    public void clearCart(String userId) {
        userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
    }
}
