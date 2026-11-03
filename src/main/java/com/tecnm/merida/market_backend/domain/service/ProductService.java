package com.tecnm.merida.market_backend.domain.service;

import com.tecnm.merida.market_backend.domain.Product;

import com.tecnm.merida.market_backend.domain.repository.ProductoRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {


    private ProductoRepository productoRepository;

    public List<Product> getAll() {
        return productoRepository.getAll();
    }

    public Optional<Product> getProduct(int productId) {
        return productoRepository.getProduct(productId);
    }

    public Optional<List<Product>> getByCategory(int categoryId) {
        return productoRepository.getByCategory(categoryId);
    }

    public Product save(Product product) {
        return productoRepository.save(product);
    }

    public boolean delete(int productId) {
        // Verificar que existe antes de borrar
        if (getProduct(productId).isPresent()) {
            productoRepository.delete(productId);
            return true;
        } else {
            return false;
        }
    }
}