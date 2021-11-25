package com.learn2code.Shop.db.service.impl;

import com.learn2code.Shop.db.repository.BoughtProductRepository;
import com.learn2code.Shop.db.service.api.BoughtProductService;
import com.learn2code.Shop.domain.BoughtProduct;
import org.springframework.stereotype.Service;

import java.util.List;

// Vela tried (povinnosti delim na viacero vrstiev)
// Lahko rozsirovatelne, citatelne

@Service
public class BoughtProductServiceImpl implements BoughtProductService {

    private final BoughtProductRepository boughtProductRepository; // constr Inject

    public BoughtProductServiceImpl(BoughtProductRepository boughtProductRepository) {
        this.boughtProductRepository = boughtProductRepository;
    }

    @Override
    public void add(BoughtProduct boughtProduct) {
        boughtProductRepository.add(boughtProduct);
    }

    @Override
    public List<BoughtProduct> getAllByCustomerId(int customerId) {
        return boughtProductRepository.getAllByCustomerId(customerId);
    }
}
