package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.service.api.MerchantSerivce;
import com.learn2code.Shop.domain.Merchant;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 3x http metody

@RestController
@CrossOrigin
@RequestMapping("merchant")
public class MerchantController {

    private final MerchantSerivce merchantSerivce;

    public MerchantController(MerchantSerivce merchantSerivce) {
        this.merchantSerivce = merchantSerivce;
    }

    @PostMapping // pridanie noveho resourse
    public ResponseEntity add(@RequestBody Merchant merchant) {
        Integer id = merchantSerivce.add(merchant);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR); // chyba v aplikacii
        }
    }

    @GetMapping("{id}")
    public ResponseEntity get(@PathVariable("id") int id) {//@PathVar.si vytiahneme param.id a vlozim do premenn. int id
        Merchant merchant = merchantSerivce.get(id); //merchServis getneme merchanta na zaklade ID a vlozime do merch.
        if (merchant == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); //404
        } else {
            return new ResponseEntity<>(merchant, HttpStatus.OK); //200
        }
    }

    @GetMapping
    public ResponseEntity getAll() { //objekt ktory charakterizuje http response
        List<Merchant> merchants = merchantSerivce.getMerchants();
        return new ResponseEntity<>(merchants, HttpStatus.OK);

    }
}
