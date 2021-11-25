package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.service.api.ShoppingService;
import com.learn2code.Shop.db.service.api.request.BuyProductRequest;
import com.learn2code.Shop.db.service.api.response.BuyProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/shop")
public class ShoppingController {
    private final ShoppingService shoppingService;

    public ShoppingController(ShoppingService shoppingService) {
        this.shoppingService = shoppingService;
    }

    @PostMapping("/buy")
    public ResponseEntity buy(@RequestBody BuyProductRequest request) {
        BuyProductResponse buyProductResponse = shoppingService.buyproduct(request);
        if (buyProductResponse.isSuccess()) {
            return ResponseEntity.ok().build();
        }else {
            return new ResponseEntity<>(buyProductResponse.getErrorMessage(), HttpStatus.PRECONDITION_FAILED); //412
        }
    }
}

