package com.tecnm.merida.market_backend.persistence;

import com.tecnm.merida.market_backend.domain.Purchase;
import com.tecnm.merida.market_backend.domain.repository.PurchaseRepository;
import com.tecnm.merida.market_backend.persistence.crud.CompraCrudRepository;
import com.tecnm.merida.market_backend.persistence.entity.Compra;
import com.tecnm.merida.market_backend.persistence.entity.CompraProducto;
import com.tecnm.merida.market_backend.persistence.entity.CompraProductoPK;
import com.tecnm.merida.market_backend.persistence.mapper.PurchaseMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class CompraRepository implements PurchaseRepository {

    private final CompraCrudRepository compraCrudRepository;
    private final PurchaseMapper mapper;

    public CompraRepository(
            CompraCrudRepository compraCrudRepository,
            PurchaseMapper mapper) {
        this.compraCrudRepository = compraCrudRepository;
        this.mapper = mapper;
    }

    @Override
    public List<Purchase> getAll() {
        return mapper.toPurchases(
                (List<Compra>) compraCrudRepository.findAll()
        );
    }

    @Override
    public Optional<List<Purchase>> getByClient(String clientId) {
        return compraCrudRepository.findByIdCliente(clientId)
                .map(mapper::toPurchases);
    }

    @Override
    public Optional<Purchase> getPurchase(Integer purchaseId) {
        return compraCrudRepository.findById(purchaseId)
                .map(mapper::toPurchase);
    }

    @Override
    @Transactional
    public Purchase save(Purchase purchase) {

        Compra compra = mapper.toCompra(purchase);

        List<CompraProducto> productos = compra.getProdustos();

        compra.setProdustos(null);

        Compra compraGuardada = compraCrudRepository.save(compra);

        if (productos != null) {
            for (CompraProducto producto : productos) {

                CompraProductoPK id = new CompraProductoPK();

                id.setIdCompra(compraGuardada.getIdCompra());
                id.setIdProducto(producto.getId().getIdProducto());

                producto.setId(id);
                producto.setCompra(compraGuardada);
            }

            compraGuardada.setProdustos(productos);

            compraGuardada = compraCrudRepository.save(compraGuardada);
        }

        return mapper.toPurchase(compraGuardada);
    }

    @Override
    @Transactional
    public Optional<Purchase> update(Purchase purchase) {

        return compraCrudRepository.findById(purchase.getPurchaseId())
                .map(compraExistente -> {

                    compraExistente.setFecha(purchase.getDate());
                    compraExistente.setMedioPago(purchase.getPaymentMethod());
                    compraExistente.setComentario(purchase.getComment());
                    compraExistente.setEstado(purchase.getState());

                    Compra actualizado =
                            compraCrudRepository.save(compraExistente);

                    return mapper.toPurchase(actualizado);
                });
    }

    @Override
    @Transactional
    public void delete(Integer purchaseId) {
        compraCrudRepository.findById(purchaseId)
                .ifPresent(compraCrudRepository::delete);
    }
}