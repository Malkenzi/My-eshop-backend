package com.learn2code.Shop.db.service.api.response;


import org.springframework.lang.Nullable;

import java.util.Objects;

// Tento response obsahuje informaciu o tom, ci sa ten nakup podaril
// ak sa nepodari nakup vyhodi aka bola chyba
public class BuyProductResponse {

    private boolean success;
    @Nullable // (ak sa nakup podari)
    private String errorMessage;

    public BuyProductResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    @Nullable
    public String getErrorMessage() {
        return errorMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BuyProductResponse that = (BuyProductResponse) o;
        return success == that.success && Objects.equals(errorMessage, that.errorMessage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(success, errorMessage);
    }

    public BuyProductResponse(boolean success, String errorMessage) {
        this.success = success;
        this.errorMessage = errorMessage;


    }
}
