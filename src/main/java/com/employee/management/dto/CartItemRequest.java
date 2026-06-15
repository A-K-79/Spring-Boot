package com.employee.management.dto;

import com.employee.management.model.Product;
import com.employee.management.model.Student;

import lombok.Data;

@Data
public class CartItemRequest {
    private Student student;
    private Product product;
    private Integer quantity;

}
