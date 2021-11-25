package com.learn2code.Shop.db.service.api.request;

// Trieda reprezentuje request(Json) ktory dostaneme v Controlleri
// pomocou neho budeme vediet indentifikovat co a kolko chce klient kupit

import java.util.Objects;

public class BuyProductRequest {
    private int productId;
    private int customerId;
    private int quantity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyProductRequest that = (BuyProductRequest) o;
        return productId == that.productId && customerId == that.customerId && quantity == that.quantity;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, customerId, quantity);
    }


    public int getProductId() {
        return productId;
    }

    public int getQuantity() {return quantity;}

    public int getCustomerId() {
        return customerId;
    }


    public BuyProductRequest(int productId, int customerId, int quantity) {
        this.productId = productId;
        this.customerId = customerId;
        this.quantity = quantity;
    }

}
