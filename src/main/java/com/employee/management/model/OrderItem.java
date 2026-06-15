package com.employee.management.model;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne // many order items can belong to one product,
    // eg: 5 packs of chips can be added to different orders by different students,
    @JoinColumn(name="product_id", nullable=false)
    private Product product;// product-id : maps to product

    private Integer quantity;
    private BigDecimal price;

    @ManyToOne // many order items can belong to one order, eg: one order can contain a lot of products.
    @JoinColumn(name="order_id", nullable=false)
    private Order order; // order-id : maps to order
}
