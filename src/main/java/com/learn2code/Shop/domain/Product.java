package com.learn2code.Shop.domain;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;

public class Product {
    @Nullable
    private Integer id;
    @Nullable
    private int merchantId;
    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private double price;
    //@NonNull
    @Nullable
    private Timestamp createdAt;
    @NonNull
    private int available;
    public Product() {

    }
    public Product(int merchantId, @NonNull String name, @NonNull String description, double price, int available) {
        this.merchantId = merchantId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.available = available;
        this.createdAt = Timestamp.from(Instant.now()); // docasne
    }

    @Nullable
    public Integer getId() {
        return id;
    }

    public void setId(@Nullable Integer id) {
        this.id = id;
    }

    public int getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(int merchantId) {
        this.merchantId = merchantId;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getDescription() {
        return description;
    }

    public void setDescription(@NonNull String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Nullable
    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(@Nullable Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public int getAvailable() {
        return available;
    }

    public void setAvailable(int available) {
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return merchantId == product.merchantId &&
                Double.compare(product.price, price) == 0 &&
                available == product.available &&
                Objects.equals(id, product.id) &&
                Objects.equals(name, product.name) &&
                Objects.equals(description, product.description) &&
                //Objects.equals(createdAt, product.createdAt);
                // miesto equals to porovname s metodou getTime
                createdAt.getTime() == product.createdAt.getTime();




    }

    @Override
    public int hashCode() {
        return Objects.hash(id, merchantId, name, description, price, createdAt, available);
    }
}
