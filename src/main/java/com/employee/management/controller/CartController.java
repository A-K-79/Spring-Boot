package com.employee.management.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.management.dto.CartItemRequest;
import com.employee.management.model.CartItem;
import com.employee.management.service.CartService;

import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor // generates a constructor with required arguments (final fields) for dependency injection.
@RequestMapping("/api/cart")
public class CartController {
    
    private final CartService cartService; // dependency injection of CartService into CartController.

    @PostMapping
    public ResponseEntity<String> addToCart(
        @RequestHeader("X-Student-ID") Integer studentId,
        @RequestBody CartItemRequest request) {
            
            if(!cartService.addToCart(studentId, request)) {
                return ResponseEntity.badRequest().body("Failed to add item to cart");
            }
            return ResponseEntity.status(HttpStatus.CREATED).body("Item added to cart successfully");
    }
    @DeleteMapping("/items/{productId}")
    public ResponseEntity<Void> removeItemFromCart(@RequestHeader ("X-Student-ID") Integer studentId, @PathVariable Integer productId) {
        if(!cartService.deleteItemFromCart(studentId, productId)) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems(@RequestHeader("X-Student-ID") Integer studentId) {
        List<CartItem> cartItems = cartService.getCartItems(studentId);
        return ResponseEntity.ok(cartItems);
    }

}
