package com.employee.management.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.employee.management.model.CartItem;
import com.employee.management.model.Product;
import com.employee.management.model.Student;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> { // repository class for CartItem entity, 
    // it is possible to perform query using JPQL, but different approach is possible
    public CartItem findByStudentAndProduct(Student student, Product product); // function to find cart item by student ID and product ID, to check if the item is already in the cart before adding it again, if it is already in the cart, we can update the quantity instead of adding a new cart item
    public void deleteByStudentIdAndProductId(Student student, Product product); // function to delete cart item by student ID and product ID, to remove an item from the cart
    List<CartItem> findByStudent(Student student); // function to find all cart items by student ID, to get all items in the cart for a specific student
}
