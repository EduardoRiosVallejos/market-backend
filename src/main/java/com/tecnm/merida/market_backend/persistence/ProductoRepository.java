package com.tecnm.merida.market_backend.persistence;

import com.tecnm.merida.market_backend.domain.Product;
import com.tecnm.merida.market_backend.persistence.crud.ProductoCrudRepository;
import com.tecnm.merida.market_backend.persistence.entity.Producto;
import com.tecnm.merida.market_backend.persistence.mapper.ProductMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductoRepository implements
        com.tecnm.merida.market_backend.domain.repository.ProductoRepository {

    private final ProductoCrudRepository productoCrudRepository;
    private final ProductMapper mapper;

    public ProductoRepository(
            ProductoCrudRepository productoCrudRepository,
            ProductMapper mapper) {
        this.productoCrudRepository = productoCrudRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Product> getAll() {
        List<Producto> productos =
                (List<Producto>) productoCrudRepository.findAll();

        return mapper.toProducts(productos);
    }

    @Override
    public Optional<List<Product>> getByCategory(int categoryId) {
        List<Producto> productos =
                productoCrudRepository.findByIdCategoriaOrderByNombreAsc(categoryId);

        if (productos.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(mapper.toProducts(productos));
    }

    @Override
    public Optional<List<Product>> getScarceProducts(int quantity) {
        return productoCrudRepository
                .findByCantidadStockLessThanAndEstado(quantity, true)
                .map(mapper::toProducts);
    }

    @Override
    public Optional<Product> getProduct(int productId) {
        return productoCrudRepository.findById(productId)
                .map(mapper::toProduct);
    }

    @Override
    public Product save(Product product) {
        Producto producto = mapper.toProducto(product);

        return mapper.toProduct(
                productoCrudRepository.save(producto)
        );
    }

    @Override
    public Optional<Product> update(Product product) {

        return productoCrudRepository.findById(product.getProductId())
                .map(productoExistente -> {

                    productoExistente.setNombre(product.getName());
                    productoExistente.setIdCategoria(product.getCategoryId());
                    productoExistente.setPrecioVenta(product.getPrice());
                    productoExistente.setCantidadStock(product.getStock());
                    productoExistente.setEstado(product.isActive());

                    Producto actualizado =
                            productoCrudRepository.save(productoExistente);

                    return mapper.toProduct(actualizado);
                });
    }

    @Override
    public void delete(int productId) {
        productoCrudRepository.deleteById(productId);
    }
}
