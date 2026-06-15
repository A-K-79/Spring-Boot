package com.employee.management.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor // this file only has this annotation because it is used in the OrderResponse class to create a list of OrderItemDTO objects.
// noargsconstructor is not needed because this class is only used to transfer data from the Order entity to the OrderResponse DTO.
public class OrderItemDTO {
    private Integer id;
    private Integer productId;
    private Integer quantity;
    private BigDecimal price;
    private BigDecimal subTotal;
}
