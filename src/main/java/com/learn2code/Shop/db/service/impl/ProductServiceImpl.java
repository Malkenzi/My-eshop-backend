package com.learn2code.Shop.db.service.impl;

import com.learn2code.Shop.db.repository.ProductRepository;
import com.learn2code.Shop.db.service.api.ProductSerivce;
import com.learn2code.Shop.db.service.api.request.UpdateProductRequest;
import com.learn2code.Shop.domain.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductSerivce {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.getAll();
    }

    @Override
    public Product get(int id) {
        return productRepository.get(id);
    }

    @Override
    public Integer add(Product product) {
        return productRepository.add(product);
    }

    @Override
    public void delete(int id) {
        productRepository.delete(id);
    }

    @Override
    public void update(int id, UpdateProductRequest updateProductRequest) {
        productRepository.udpate(id, updateProductRequest);
    }

    //dodatocna funkcia setujuca nove available do produktu
    @Override
    public void updateAvailableInternal(int id, int newAvailable) {
        productRepository.updateAvailable(id, newAvailable);
    }
}
