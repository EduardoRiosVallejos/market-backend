
package com.tecnm.merida.market_backend.web.controller;

import com.tecnm.merida.market_backend.domain.Category;
import com.tecnm.merida.market_backend.domain.service.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@Tag(name = "Category", description = "Manage your categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("")
    public ResponseEntity<List<Category>> getAll() {
        return new ResponseEntity<>(
                categoryService.getAll(),
                HttpStatus.OK
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategory(
            @PathVariable("id") int categoryId) {

        return categoryService.getCategory(categoryId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public ResponseEntity<Category> save(
            @RequestBody Category category) {

        return new ResponseEntity<>(
                categoryService.save(category),
                HttpStatus.CREATED
        );
    }
    @PutMapping("/{id}")
    public ResponseEntity<Category> update(
            @PathVariable("id") int categoryId,
            @RequestBody Category category) {

        category.setCategoryId(categoryId);

        return categoryService.update(category)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable("id") int categoryId) {

        if (categoryService.delete(categoryId)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
