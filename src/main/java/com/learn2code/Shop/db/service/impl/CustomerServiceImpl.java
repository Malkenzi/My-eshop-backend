package com.learn2code.Shop.db.service.impl;

import com.learn2code.Shop.db.repository.CustomerRepository;
import com.learn2code.Shop.db.service.api.CustomerSerivce;
import com.learn2code.Shop.domain.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

// funkcionalita nakupu
// Mohol sm Customer Repository robit aj tu a mal by som menej tried kedze nic ine
// Ked budem implementovat nakup tak chcem aby sa Business logika odohravala tuna
// Service sa robi aby sa tu pisala logika

@Service // A @Component robi vpodstate to iste
public class CustomerServiceImpl implements CustomerSerivce {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    } // Constructor Injection - mozny vdaka anotacii @Component v CustomerRepository

    @Override
    public List<Customer> getCustomers() {
        return customerRepository.getAll();
    }

    @Override
    public Customer get(int id) {
        return customerRepository.get(id);
    }

    @Override
    public Integer add(Customer customer) {
        return customerRepository.add(customer);
    }
}
