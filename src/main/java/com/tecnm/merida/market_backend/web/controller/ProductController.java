package com.tecnm.merida.market_backend.web.controller;

import com.tecnm.merida.market_backend.domain.Product;
import com.tecnm.merida.market_backend.domain.service.ProductService;
import com.tecnm.merida.market_backend.persistence.ProductoRepository;
import com.tecnm.merida.market_backend.persistence.crud.ProductoCrudRepository;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.TableGenerator;
import org.apache.catalina.connector.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Manage your products")
public class ProductController {
    private ProductService productService;
    private ProductoRepository productoRepository;

    @GetMapping("")

    @Operation(summary = "Get all products",
            description = "Return a list of all available products")
    @ApiResponse(responseCode = "200",
            description = "Successful retrieval of products")
    @ApiResponse(responseCode = "500",
            description = "Internal server error")
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(productService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get products by category",
            description = "Return all products in a specific category")
    @ApiResponse(responseCode = "200",
            description = "Product(s) found in the category")
    @ApiResponse(responseCode = "404",
            description = "Product(s) not found in the category")
    @ApiResponse(responseCode = "500",
            description = "Internal server error")
    public ResponseEntity<List<Product>> getByCategory(
            @Parameter(description = "Category ID",
                    example = "7", required = true)
            int categoryId){
        return productService.getByCategory(categoryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @PostMapping("")
    public ResponseEntity<Product> save(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Product> delete(@PathVariable("id") int productId){
        if (productService.delete(productId)){
            return ResponseEntity.ok().build();

        }{
            return ResponseEntity.notFound().build();
        }
    }
}