package com.tecnm.merida.market_backend.web.controller;

import com.tecnm.merida.market_backend.domain.Purchase;
import com.tecnm.merida.market_backend.domain.service.PurchaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {

    private final PurchaseService purchaseService;

    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }

    @GetMapping
    public List<Purchase> getAll() {
        return purchaseService.getAll();
    }

    @GetMapping("/client/{clientId}")
    public Optional<List<Purchase>> getByClient(
            @PathVariable String clientId) {
        return purchaseService.getByClient(clientId);
    }

    @GetMapping("/{id}")
    public Optional<Purchase> getPurchase(@PathVariable Integer id) {
        return purchaseService.getPurchase(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Purchase save(@RequestBody Purchase purchase) {
        return purchaseService.save(purchase);
    }

    @PutMapping("/{id}")
    public Optional<Purchase> update(
            @PathVariable Integer id,
            @RequestBody Purchase purchase) {

        purchase.setPurchaseId(id);
        return purchaseService.update(purchase);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        purchaseService.delete(id);
    }
}