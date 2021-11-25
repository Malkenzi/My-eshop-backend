package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.service.api.BoughtProductService;
import com.learn2code.Shop.domain.BoughtProduct;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bought-product") // ako budu zacinat volania ?

public class BoughtProductController {

    private final BoughtProductService boughtProductService;

    public BoughtProductController(BoughtProductService boughtProductService) {
        this.boughtProductService = boughtProductService;
    }

    // mozem vidiet kupene produkty na zaklade custId (budeme vediet nakupovat)
    @GetMapping("{customerId}") //get metoda  //path variable
    public ResponseEntity getByCustomerId(@PathVariable("customerId") int customerId) {//metoda kt. vracia responseEntity
        List<BoughtProduct> boughtProductList = boughtProductService.getAllByCustomerId(customerId);
        // zavolal som boughtProductService, getol customerId, setol do boughtProductListu

        //trz potrebujeme vratit ResponseEnity
        return new ResponseEntity<>(boughtProductList, HttpStatus.OK); // do som dal Json, a status bude 200
    }
}