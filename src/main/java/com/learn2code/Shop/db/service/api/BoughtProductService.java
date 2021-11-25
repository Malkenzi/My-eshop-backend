package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.domain.BoughtProduct;

import java.util.List;

public interface BoughtProductService {

    // pridanie noveho kupeneho produktu (shopping service)
    void add(BoughtProduct boughtProduct);

    // na zaklade customer id vraciat vsetky kupene produkty (controller)
    List<BoughtProduct> getAllByCustomerId(int customerId);
}
