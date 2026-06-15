package com.employee.management.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import com.employee.management.model.OrderStatus;

import lombok.Data;

@Data
public class OrderResponse {
    private Integer id;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private List<OrderItemDTO> items;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
