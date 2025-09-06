package com.app.ecom.service;


import com.app.ecom.dto.order.OrderItemDTO;
import com.app.ecom.dto.order.OrderResponse;

import com.app.ecom.entites.order.CartItem;
import com.app.ecom.entites.order.Order;
import com.app.ecom.entites.order.OrderItem;
import com.app.ecom.entites.order.OrderStatus;
import com.app.ecom.entites.user.User;
import com.app.ecom.repository.OrderRepository;
import com.app.ecom.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class OrderService {
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private final CartItemServices cartItemServices;


    public Optional<OrderResponse> createOrder(String userId) {
       Optional<User> userOpt =userRepository.findById(Long.valueOf(userId));
       List<CartItem> cartItems=cartItemServices.fetchAllCartItems(userId);

       if (userOpt.isEmpty()) {
           return Optional.empty();
       }
        if (cartItems.isEmpty()) {
            return Optional.empty();
        }
        User user = userOpt.get();
        BigDecimal total=cartItems.stream().
                map(CartItem::getPrice).
                reduce(BigDecimal.ZERO,BigDecimal::add);

        Order order=new Order();
        order.setUser(user);
        order.setTotal(total);
        order.setStatus(OrderStatus.Confirmed);

        List<OrderItem> orderItems= cartItems.stream().
                map(item ->  new OrderItem
                        (null,item.getProduct(), item.getQuantity(),
                                item.getPrice(),order)).toList();
        order.setItem(orderItems);

        Order savedOrder=orderRepository.save(order);


        cartItemServices.clearCart(userId);


        return Optional.of(mapToOrderResponse(savedOrder));
    }


    private OrderResponse mapToOrderResponse(Order order) {
      return new OrderResponse(order.getId(),order.getTotal(),order.getStatus(),
                            order.getItem().stream().map(orderItem -> new OrderItemDTO(
                                    orderItem.getId(),orderItem.getProduct().getId(),
                                    orderItem.getQuantity(),orderItem.getPrice()
                )).toList(),order.getCreatedAt());
    }

}
