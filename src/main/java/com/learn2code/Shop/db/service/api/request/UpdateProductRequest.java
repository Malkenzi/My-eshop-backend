package com.learn2code.Shop.db.service.api.request;

import org.springframework.lang.NonNull;

import java.util.Objects;

public class UpdateProductRequest {

    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private Double price;
    @NonNull
    private int available;

    public UpdateProductRequest(@NonNull String name, @NonNull String description, @NonNull Double price, int available) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
    }

    @NonNull
    public String getName() {
        return name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    @NonNull
    public Double getPrice() {
        return price;
    }

    public int getAvailable() {
        return available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UpdateProductRequest that = (UpdateProductRequest) o;
        return available == that.available && name.equals(that.name) && description.equals(that.description) && price.equals(that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, price, available);
    }
}
