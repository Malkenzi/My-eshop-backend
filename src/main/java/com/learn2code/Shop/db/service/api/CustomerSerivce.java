package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.domain.Customer;
import org.springframework.lang.Nullable;

import java.util.List;
// Databazove rozhrania
                                            //*  metody co s danou entitou chem vediet robit  *//
public interface CustomerSerivce {

    List<Customer> getCustomers();            //vrati vstkych zakaznikov

    @Nullable
    Customer  get(int id);                   //vrati jedneho zakaznika na zaklade jeho ID

    @Nullable //(pre istotku) moze nastat nejaka chyba
     Integer add(Customer customer);        //pridanie noveho customera // returns generated id


}
