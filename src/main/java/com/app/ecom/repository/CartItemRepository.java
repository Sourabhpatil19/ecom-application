package com.app.ecom.repository;

import com.app.ecom.entites.order.CartItem;
import com.app.ecom.entites.product.Product;
import com.app.ecom.entites.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem,Long> {

    List<CartItem> user(User user);

    CartItem findByUserAndProduct(User user, Product product);

    void deleteByUserAndProduct(User user, Product product);


    List<CartItem> findByUser(User user);

    void deleteByUser(User user);
}
