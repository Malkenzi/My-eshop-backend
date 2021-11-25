package com.learn2code.Shop.db.service.impl;

import com.learn2code.Shop.db.repository.CustomerAccountRepository;
import com.learn2code.Shop.db.service.api.CustomerAccountService;
import com.learn2code.Shop.domain.CustomerAccount;
import org.springframework.stereotype.Service;

// Implementacia

@Service // to create Bean (aby sa to dalo pouzivat vsade)
public class CustomerAccountServiceImpl implements CustomerAccountService {

    private final CustomerAccountRepository customerAccountRepository;

    public CustomerAccountServiceImpl(CustomerAccountRepository customerAccountRepository) {
        this.customerAccountRepository = customerAccountRepository;
    }

    @Override
    public void addCustomerAccount(CustomerAccount customerAccount) {
        customerAccountRepository.add(customerAccount);
    }

    // get/set Nebudem volat Restovou sluzbou ale az v ShoppingSerivce
    @Override
    public Double getMoney(int customerID) {
        return customerAccountRepository.getMoney(customerID);
    }

    @Override
    public void setMoney(int customerID, double money) {
        customerAccountRepository.setMoney(customerID, money);
    }
}
