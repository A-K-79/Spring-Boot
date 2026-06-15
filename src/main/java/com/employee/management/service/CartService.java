package com.employee.management.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.employee.management.dto.CartItemRequest;
import com.employee.management.model.CartItem;
import com.employee.management.model.Product;
import com.employee.management.model.Student;
import com.employee.management.repository.CartItemRepository;
import com.employee.management.repository.ProductRepository;
import com.employee.management.repository.StudentRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
    private final ProductRepository productRepository;
    private final StudentRepository studentRepository; 
    private final CartItemRepository cartItemRepository;

    public boolean addToCart(Integer studentId, CartItemRequest request) {
        Optional<Product> productOpt = productRepository.findById(request.getProduct().getId());
        if (productOpt.isEmpty()) { // Check if the product exists in the database
            return false; // Product not found
        }
        Product product = productOpt.get(); // if product exists

        Integer requestedQuantity = request.getQuantity();
        Integer availableStock = product.getStockQuantity();

        if(availableStock < requestedQuantity){ //Check if the required amount is available
            return false; // Not enough stock available
        }

        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) { // Check if the student exists in the database
            return false; // Student not found
        }

        Student student = studentOpt.get(); // if student exists
        CartItem existingCartItem = cartItemRepository.findByStudentAndProduct(student, product); // Check if the student already has this product in their cart
        if(existingCartItem==null){
            CartItem cartItem = new CartItem();
            cartItem.setStudent(request.getStudent());
            cartItem.setProduct(request.getProduct());
            cartItem.setQuantity(request.getQuantity());

            // Calculate the price based on the quantity and the product price (BigDecimal convertion is necessary to make both operaands the same type)
            cartItem.setPrice( BigDecimal.valueOf(cartItem.getQuantity()).multiply(product.getPrice()) );

            cartItemRepository.save(cartItem);
        }else{
            // if the student already has this product in their cart, we can update the quantity instead of adding a new cart item
            existingCartItem.setQuantity(existingCartItem.getQuantity() + requestedQuantity); 

            // Update the price based on the new quantity (BigDecimal convertion is necessary to make both operaands the same type)
            existingCartItem.setPrice( BigDecimal.valueOf(existingCartItem.getQuantity()).multiply(product.getPrice()) ); 

            cartItemRepository.save(existingCartItem);
        }

        return true; // Successfully added to cart
    }

    @Transactional
    public boolean deleteItemFromCart(Integer studentId, Integer productId) {
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isEmpty() || studentOpt.isEmpty()) { // Check if the product exists in the database
            return false; // Product not found
        }

        Student student = studentOpt.get();
        Product product = productOpt.get();

        CartItem existingCartItem = cartItemRepository.findByStudentAndProduct(student, product);
        if (existingCartItem == null) {
            return false; // Cart item not found
        }

        cartItemRepository.deleteByStudentIdAndProductId(student, product);
        return true; // Successfully removed from cart
    }

    public List<CartItem> getCartItems(Integer studentId) {
         Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) { // Check if the student exists in the database
            return List.of(); // Student not found, return empty list
        }

        Student student = studentOpt.get();
        return cartItemRepository.findByStudent(student);
    }
}
