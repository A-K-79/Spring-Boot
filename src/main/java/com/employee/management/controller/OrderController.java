package com.employee.management.controller;

import org.springframework.http.HttpStatus;

import com.employee.management.dto.OrderResponse;
import com.employee.management.service.OrderService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import com.employee.management.service.OrderServices;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {
    private final OrderServices orderService; // dependency injection of OrderService into OrderController.
    @PostMapping
    public ResponseEntity<OrderResponse> addOrder(@RequestHeader("X-Student-Id") Integer studentId) {

        return orderService.createOrder(studentId)
        .map(orderResponse -> new ResponseEntity<>(orderResponse, HttpStatus.CREATED))// true part
        .orElseGet(() -> ResponseEntity.badRequest().build()); // false part
    }
}
