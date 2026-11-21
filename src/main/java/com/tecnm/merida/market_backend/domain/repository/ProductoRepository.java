package com.tecnm.merida.market_backend.domain.repository;

import com.tecnm.merida.market_backend.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    List<Product> getAll();
    Optional<List<Product>> getByCategory(int categoryId);
    Optional<List<Product>> getScarceProducts(int quantity);
    Optional<Product> getProduct(int productId);
    Product save(Product product);
    Optional<Product> update(Product product);


    void delete(int productId);
}