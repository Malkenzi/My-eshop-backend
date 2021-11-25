package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.domain.CustomerAccount;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

// komandy posielane do nasho mysql servera

@Component
public class CustomerAccountRepository {
    private final JdbcTemplate jdbcTemplate;

    public CustomerAccountRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(CustomerAccount customerAccount) {
        final String sql = "INSERT INTO customer_account(customer_id, money) values(?,?)";
        jdbcTemplate.update(sql, customerAccount.getCustomerID(), customerAccount.getMoney());

    }
    @Nullable
    public Double getMoney(int customerID) {
        final String sql = "SELECT money FROM customer_account WHERE customer_id = " + customerID;
        try {
            return jdbcTemplate.queryForObject(sql, Double.class);
        } catch (DataAccessException e) {
            return null;
        }
    }
    public void setMoney(int customerId, double money) {
        final String sql = "UPDATE customer_account SET money = ? where customer_id = ?";
        jdbcTemplate.update(sql, money, customerId);
    }


}
