package com.employee.management.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.employee.management.dto.ProductRequest;
import com.employee.management.dto.ProductResponse;
import com.employee.management.model.Product;
import com.employee.management.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServices {
    private final ProductRepository prorepo;
    public ProductResponse addProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromRequest(product, productRequest);
        Product savedProduct = prorepo.save(product);
        return mapToProductResponse(savedProduct);
    }

    public Optional<ProductResponse> updateProd(Integer id, ProductRequest preq) {
        return prorepo.findById(id)
                .map(product -> {
                    updateProductFromRequest(product, preq);
                    Product updatedProduct = prorepo.save(product);
                    return mapToProductResponse(updatedProduct);
                });
    }

    private void updateProductFromRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setDescription(productRequest.getDescription());
        product.setPrice(productRequest.getPrice());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
        
        product.setCategory(productRequest.getCategory());
    }
    


    private ProductResponse mapToProductResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStockQuantity(product.getStockQuantity());
        response.setImageUrl(product.getImageUrl());
        response.setCategory(product.getCategory());
        return response;
    }

    public List<ProductResponse> getAllProducts() {
        return prorepo.findAll().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(Integer id){ // this is a predefined method for product repository
        return prorepo.findById(id)
        .map(this::mapToProductResponse) // true part
        .orElseThrow(() -> new RuntimeException("Product not found in id: " + id)); // false part
    }

    

    public String deleteProduct(Integer id){ // currenty unavailable product
        Product product = prorepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Product not found in id: " + id));
        product.setActive(false);
        return "Product deactivated";
    }

    public List<ProductResponse> searchProduct(String keyword) {
        return prorepo.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }
}

//.map is the true part and .orElseThrow is the false part of the optional.