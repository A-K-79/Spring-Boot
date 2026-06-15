package com.employee.management.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity(name = "Cart-Wheel") // create a table with name Cart-Wheel


public class CartItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;
    
    @ManyToOne // many cart items can belong to one student, eg: one student can add a lot of products to the cart. 
    @JoinColumn(name="student_id", nullable=false) // foreign key column in CartItem table referencing Student table, 
    // nullable=false means that every cart item must be associated with a student, ie student_id cannot be null in the CartItem table
    private Student student;

    @ManyToOne // many cart items can belong to one product, 
    // eg: 5 packs of chips can be added to different carts by different students, but they all refer to the same product in the Product table.
    @JoinColumn(name="product_id", nullable=false) // foreign key column in CartItem table referencing Product table,
    // nullable=false means that every cart item must be associated with a product, ie product_id cannot be null in the CartItem table
    private Product product;

    private Integer quantity;
    private BigDecimal price;
    @CreationTimestamp
    private LocalDateTime createdAt;
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
