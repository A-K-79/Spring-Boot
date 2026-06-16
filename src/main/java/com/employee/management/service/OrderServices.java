package com.employee.management.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.employee.management.dto.OrderItemDTO;
import com.employee.management.dto.OrderResponse;
import com.employee.management.model.CartItem;
import com.employee.management.model.Order;
import com.employee.management.model.OrderItem;
import com.employee.management.model.OrderStatus;
import com.employee.management.model.Student;
import com.employee.management.repository.OrderRepository;
import com.employee.management.repository.StudentRepository;

import lombok.RequiredArgsConstructor;



@Service
@RequiredArgsConstructor
public class OrderServices {

    private final CartService cartService;
    private final StudentRepository studentRepository;
    private final OrderRepository orderRepository;

    public Optional<OrderResponse> createOrder(Integer studentId) {
        // Validate the cart
        List<CartItem> cartItems = cartService.getCartItems(studentId);
        if(cartItems.isEmpty()) {
            return Optional.empty(); // No items in the cart, cannot create an order
        }
        //Validate the user
        Optional<Student> studentOpt = studentRepository.findById(studentId);
        if (studentOpt.isEmpty()) {
            return Optional.empty(); // Student not found, cannot create an order
        }
        Student student = studentOpt.get();
        // Calculate total amount
        BigDecimal totalPrice = cartItems.stream()
                .map(CartItem::getPrice) // why map is used: map is used to transform each CartItem into its price (BigDecimal). It takes each CartItem from the stream and applies the getPrice() method to extract the price.
                .reduce(BigDecimal.ZERO, BigDecimal::add); // reduce is used to aggregate the prices into a single total amount. It starts with an initial value of BigDecimal.ZERO and then adds each price to it using the add method. The result is the total price of all items in the cart.
        // Create order 
        Order order = new Order();
        order.setStudent(student);
        order.setStatus(OrderStatus.CONFIRMED);
        order.setTotalAmount(totalPrice);

        List<OrderItem> orderItems = cartItems.stream()
                .map(item -> new OrderItem(
                    null,
                    item.getProduct(),
                    item.getQuantity(),
                    item.getPrice(),
                    order
                ))
                .toList();
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);
        // Clear the cart
        cartService.clearCart(studentId);
        return Optional.of(mapToOrderResponse(savedOrder));
    }
    private OrderResponse mapToOrderResponse(Order savedOrder) {
        return new OrderResponse(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getStatus(),
                savedOrder.getItems().stream()
                        .map(orderItem -> new OrderItemDTO(
                                orderItem.getId(),
                                orderItem.getProduct().getId(),
                                orderItem.getQuantity(),
                                orderItem.getPrice(),
                                orderItem.getPrice().multiply(new BigDecimal(orderItem.getQuantity()))
                            )).toList(),
                        savedOrder.getCreatedAt()
        );
    }
}
