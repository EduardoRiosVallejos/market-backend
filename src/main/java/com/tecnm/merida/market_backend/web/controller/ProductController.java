
package com.tecnm.merida.market_backend.web.controller;

import com.tecnm.merida.market_backend.domain.Product;
import com.tecnm.merida.market_backend.domain.service.ProductService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Manage your products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(
                productService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Product>> getByCategory(
            @Parameter(description = "The category ID")
            @PathVariable("id") int categoryId) {

        return productService.getByCategory(categoryId)
                .map(products -> new ResponseEntity<>(products, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("")
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(
                productService.save(product),
                HttpStatus.CREATED
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<Product> update(
            @PathVariable("id") int productId,
            @RequestBody Product product) {

        product.setProductId(productId);

        Optional<Product> updatedProduct = productService.update(product);

        return updatedProduct
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") int productId) {

        if (productService.delete(productId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
