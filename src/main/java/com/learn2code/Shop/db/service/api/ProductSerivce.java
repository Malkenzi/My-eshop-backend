package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.db.service.api.request.UpdateProductRequest;
import com.learn2code.Shop.domain.Product;
import org.springframework.lang.Nullable;

import java.util.List;

public interface ProductSerivce {

    List<Product> getProducts();        //vrati vsetky produkty

    @Nullable
// IDcko nemusi existovat
    Product get(int id);                // vrati produkt na zaklade jeho ID

    @Nullable
    Integer add(Product product);       // returns generaated ID

    void delete(int id);

    void update(int id, UpdateProductRequest updateProductRequest);
    // This method update: Id produktu ktory sa ma zmenit, request (tie hodnoty na ktore sa ma zmenit)

    void updateAvailableInternal(int id, int newAvailable);
}
