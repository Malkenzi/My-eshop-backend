package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.service.api.CustomerSerivce;
import com.learn2code.Shop.db.service.api.ProductSerivce;
import com.learn2code.Shop.db.service.api.request.UpdateProductRequest;
import com.learn2code.Shop.domain.Product;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// 5x http metod
// prida produkt, ziska vsetky, jeden, upravit, vymazat CRUD

@RestController
@CrossOrigin
@RequestMapping("product")
public class ProductController {

    private final ProductSerivce productSerivce;

    public ProductController(ProductSerivce productSerivce) {
        this.productSerivce = productSerivce;
    }

    @PostMapping
    public ResponseEntity addProduct(@RequestBody Product product) {
        //v requeste nam pride Json a jeho body si namapujeme na nas objekt 'product'
        // (Json sa premeni na tento product)

        Integer id = productSerivce.add(product);
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> getProduct(@PathVariable("id") int id) {
        Product product = productSerivce.get(id);
        if (product == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(product, HttpStatus.OK);
        }
    }

    @GetMapping
    public ResponseEntity<Iterable<Product>> getAllProducts() {
        List<Product> productList = productSerivce.getProducts();
        return new ResponseEntity<>(productList, HttpStatus.OK);
    }

    // UPDATE !!!
    // PUT (vsetky parametre resourcu)
    // PATCH (cast produktu) <---- nas pripad
    @PatchMapping("{id}")
    public ResponseEntity updateProduct(@PathVariable("id") int id, @RequestBody UpdateProductRequest request) {
        if (productSerivce.get(id) != null) {   // ak produkt existuje
            productSerivce.update(id, request); // update=void
            return ResponseEntity.ok().build(); // novy sposob // prazdne telo , HttpStatus.OK
        } else {
            //PreconditionFailed (412) -> Nejake ocakavanie ze to ID bude existovat nebolo splnene
            //Neocakavam ziaden resource lebo update je void
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("Product with id" + id + " does not exist");
        }
    }

    // DELETE !!!
    @DeleteMapping("{id}")
    public ResponseEntity deleteProduct(@PathVariable("id") int id) {
        if (productSerivce.get(id) != null) {
            productSerivce.delete(id);
            return ResponseEntity.ok().build();
        }else{
            return ResponseEntity
                    .status(HttpStatus.PRECONDITION_FAILED)
                    .body("Product with id: " + id + " does not exist");
        }
    }
}

