package com.employee.management.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Entity(name="orders")
@AllArgsConstructor
@RequiredArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // many orders can belong to one student, eg: one student can place a lot of orders.
    @JoinColumn(name="student_id", nullable=false)
    private Student student;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING) // Store the enum as a string in the database(e.g., "PENDING", "CONFIRMED", etc.)
    private OrderStatus status=OrderStatus.PENDING;

    @OneToMany(mappedBy="order", cascade=CascadeType.ALL, orphanRemoval=true) // one order can contain many order items, eg: one order can contain a lot of products.
    // orderitem ceases to exist if the order is deleted, because of orphanRemoval=true, and cascade=CascadeType.ALL means that any operation performed on the order will be cascaded to the order items, eg: if we delete an order, all its order items will be deleted as well.
    private List<OrderItem> items=new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
