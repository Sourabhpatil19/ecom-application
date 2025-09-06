package com.app.ecom.entites.order;

import com.app.ecom.entites.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "orders")
@Data
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;
    private BigDecimal total;
    @Enumerated(EnumType.STRING)
    private OrderStatus status= OrderStatus.Pending;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderItem> item=  new ArrayList<>();
    @CreationTimestamp
    private  LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
