package com.bookcharm.app.service;

import com.bookcharm.app.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(Long productId);
    
    Product updateProduct(Long productId, Product product);
    boolean deleteProduct(Long productId);
    // Other ProductService methods
	Product addProduct(Product product);
}
