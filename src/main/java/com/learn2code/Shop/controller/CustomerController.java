package com.learn2code.Shop.controller;

import com.learn2code.Shop.db.service.api.CustomerAccountService;
import com.learn2code.Shop.db.service.api.CustomerSerivce;
import com.learn2code.Shop.domain.Customer;
import com.learn2code.Shop.domain.CustomerAccount;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// V controlleroch vystavujeme HTTP sluzby ktore moze klient cez frontEnd zavolat

// CUSTOMER - Rest api implementacia

// Sluzba    HHTP    URL            BODY
// getall   GET    /customer         x
// get      GET    /customer/{id}    x
// add      POST   /customer        Customer

@RestController
// kazdy RQ zacina prefixom Customer
@CrossOrigin
@RequestMapping("customer") // kazda urlka v endpointe bude zacinat endpointom
public class CustomerController {

    private final CustomerSerivce customerSerivce; // budem potrebovat v jednotlivych metodach
    private final CustomerAccountService customerAccountService;

    public CustomerController(CustomerSerivce customerSerivce, CustomerAccountService customerAccountService) {
        this.customerSerivce = customerSerivce;
        this.customerAccountService = customerAccountService;
    }

    @GetMapping   // Anot na http get metodu
    public ResponseEntity getAll() { // metoda get all , kazda metoda bude vraciat ResponceEntity
        // objekt ktory charakterizuje http response

        List<Customer> customerList = customerSerivce.getCustomers();
        return new ResponseEntity<>(customerList, HttpStatus.OK); // chceme vratit responce entity
        //kazdy responce obsahuje nejaky status (HTTP status - 200)
    }

    //add
    @PostMapping //http post metoda
    public ResponseEntity add(@RequestBody Customer customer) { // budem posielat customera
        //body si mozeme vytiahnut do nasho objektu customer tak, ze si dame anot. ReqBody

        Integer id = customerSerivce.add(customer); // pridava customera a vracia Integer
        if (id != null) {
            return new ResponseEntity<>(id, HttpStatus.CREATED); // 201 //ak sa nam podarilo setnut noveho customera
        } else {
            return new ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    @GetMapping("{id}") //ocakavam dalsi parameter ktory si mozeme ulozit pod id
    public ResponseEntity get(@PathVariable("id") int id) {
        // pomocou Anot. @PathVariable som si parameter id vytiahol a vlozil do premennej id
        Customer customer = customerSerivce.get(id);
        if (customer == null) {          //kedze Customer get je Nullable (idcko nemusi najst..)
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); //404
        }
        return new ResponseEntity<>(customer, HttpStatus.OK);    //200
    }

    // CUSTOMER ACCOUNT CONTROLLER (mozeme pridat zakaznikovy account)
    @PostMapping("/account")
    public ResponseEntity<CustomerAccount> addAccount(@RequestBody CustomerAccount customerAccount) {
        customerAccountService.addCustomerAccount(customerAccount);
        return new ResponseEntity<>(customerAccount, HttpStatus.CREATED); // netreba vratit ID, staci 201

    }
}