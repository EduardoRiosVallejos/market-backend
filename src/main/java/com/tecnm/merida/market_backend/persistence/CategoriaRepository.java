
package com.tecnm.merida.market_backend.persistence;

import com.tecnm.merida.market_backend.domain.Category;
import com.tecnm.merida.market_backend.persistence.crud.CategoriaCrudRepository;
import com.tecnm.merida.market_backend.persistence.entity.Categoria;
import com.tecnm.merida.market_backend.persistence.mapper.CategoryMapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CategoriaRepository implements
        com.tecnm.merida.market_backend.domain.repository.CategoryRepository {

    private final CategoriaCrudRepository categoriaCrudRepository;
    private final CategoryMapper mapper;

    public CategoriaRepository(
            CategoriaCrudRepository categoriaCrudRepository,
            CategoryMapper mapper) {
        this.categoriaCrudRepository = categoriaCrudRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Category> getAll() {
        List<Categoria> categorias = new ArrayList<>();
        categoriaCrudRepository.findAll().forEach(categorias::add);
        return categorias.stream()
                .map(mapper::toCategory)
                .toList();
    }

    @Override
    public Optional<Category> getCategory(int categoryId) {
        return categoriaCrudRepository.findById(categoryId)
                .map(mapper::toCategory);
    }

    @Override
    public Category save(Category category) {
        Categoria categoria = mapper.toCategoria(category);
        return mapper.toCategory(categoriaCrudRepository.save(categoria));
    }

    @Override
    public Optional<Category> update(Category category) {
        return categoriaCrudRepository.findById(category.getCategoryId())
                .map(categoriaExistente -> {
                    categoriaExistente.setDescription(category.getCategory());
                    categoriaExistente.setEstado(category.isActive());

                    Categoria actualizada =
                            categoriaCrudRepository.save(categoriaExistente);

                    return mapper.toCategory(actualizada);
                });
    }
    @Override
    public void delete(int categoryId) {
        categoriaCrudRepository.deleteById(categoryId);
    }
}
