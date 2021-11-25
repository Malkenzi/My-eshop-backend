package com.learn2code.Shop.db.service.impl;

import com.learn2code.Shop.db.service.api.*;
import com.learn2code.Shop.db.service.api.request.BuyProductRequest;
import com.learn2code.Shop.db.service.api.response.BuyProductResponse;
import com.learn2code.Shop.domain.BoughtProduct;
import com.learn2code.Shop.domain.Customer;
import com.learn2code.Shop.domain.Product;
import org.springframework.stereotype.Service;


@Service
public class ShoppingServiceImpl implements ShoppingService {

    private final ProductSerivce productSerivce;
    private final CustomerSerivce customerSerivce;
    private final CustomerAccountService customerAccountService;
    private final BoughtProductService boughtProductService;

    public ShoppingServiceImpl
            (ProductSerivce productSerivce, CustomerSerivce customerSerivce, CustomerAccountService customerAccountService, BoughtProductService boughtProductService) {
        this.productSerivce = productSerivce;
        this.customerSerivce = customerSerivce;
        this.customerAccountService = customerAccountService;
        this.boughtProductService = boughtProductService;
    }

    @Override
    public BuyProductResponse buyproduct(BuyProductRequest request) {
        int productId = request.getProductId();
        int customerId = request.getCustomerId();

        Product product = productSerivce.get(productId);
        // Ak neexistuje produkt
        if (product == null) {
            return new BuyProductResponse
                    (false, "product with id: " + productId + " doesnt exist");
        }
        Customer customer = customerSerivce.get(customerId);
        // Ak neexistuje customer
        if (customer == null) {
            return new BuyProductResponse
                    (false, "Custmer with id: " + customerId + " doesnt exist");
        }
        // Dostatocny pocet dostupnych produktov Available? (ako quantity v Requeste) ?
        if (product.getAvailable() < request.getQuantity()) {
            return new BuyProductResponse
                    (false, "Not enough products in stock");

        }


        Double customerMoney = customerAccountService.getMoney(customerId);
        // Ci ma vobec customer ucet
        if (customerMoney == null) {
            return new BuyProductResponse
                    (false, "Customer with id: " + customerId + "doesnt have acc");
            // !!!
        } else {
            double totalPriceOfRequest = product.getPrice() * request.getQuantity();
            //Ak existuje a (posledna validacia) ak ma dostatok penazi
            if (customerMoney >= totalPriceOfRequest) {

                //dostupnost (na sklade)
                productSerivce.updateAvailableInternal(productId, product.getAvailable() - request.getQuantity());
                //peniaze zakaznika
                customerAccountService.setMoney(customerId, customerMoney - totalPriceOfRequest);
                //list of orders
                boughtProductService.add(new BoughtProduct(productId, customerId, request.getQuantity()));
                return new BuyProductResponse(true);

            } else {
                return new BuyProductResponse
                        (false, "Customer with id: " + customerId + "doesnt have enough moeny");
            }
        }
    }
}