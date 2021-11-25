package com.learn2code.Shop.db.service.api;

import com.learn2code.Shop.domain.CustomerAccount;
import org.springframework.lang.Nullable;

public interface CustomerAccountService {

    void addCustomerAccount(CustomerAccount customerAccount);

    @Nullable // ked nenajde nic na zaklade customerID
    Double getMoney(int customerID);

    void setMoney(int customerID, double money);


}
