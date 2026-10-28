package com.tecnm.merida.market_backend.persistence.crud;
import com.tecnm.merida.market_backend.domain.Product;
import com.tecnm.merida.market_backend.persistence.entity.Producto;
import org.springframework.data.repository.CrudRepository;
import java.util.List;
import java.util.Optional;






//Metodos abstractos que despues se implementan
public interface ProductoCrudRepository extends CrudRepository<Producto, Integer> {
    /*SQL Query Method
    SELECT *
    FROM productos
    WHERE id_categoria = 10?
    ORDER BY nombre ASC

     */
    public interface ProductoRepository {
        Optional<List<Product>> getByCategory(int categoryId);

        Optional<List<Product>> getScarceProducts(int quantity);

        Optional<Product> getProduct(int productId);

        Product save(Product product);

        void delete(int productoId);
    }

    List<Producto> findByIdCategoriaOrderByNombreAsc(int idCategoria);

    //Cantidad Stock
    Optional<List<Producto>> findByCantidadStockLessThanAndEstado(int cantidadStock, boolean estado);

}