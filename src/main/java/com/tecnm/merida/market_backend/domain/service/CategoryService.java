
package com.tecnm.merida.market_backend.domain.service;

import com.tecnm.merida.market_backend.domain.Category;
import com.tecnm.merida.market_backend.domain.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {
        return categoryRepository.getAll();
    }

    public Optional<Category> getCategory(int categoryId) {
        return categoryRepository.getCategory(categoryId);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> update(Category category) {
        return categoryRepository.update(category);
    }

    public boolean delete(int categoryId) {
        if (getCategory(categoryId).isPresent()) {
            categoryRepository.delete(categoryId);
            return true;
        } else {
            return false;
        }
    }
}
