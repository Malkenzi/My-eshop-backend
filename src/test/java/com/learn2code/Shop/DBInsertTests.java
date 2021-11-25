package com.learn2code.Shop;
import com.learn2code.Shop.domain.Customer;
import com.learn2code.Shop.domain.Merchant;
import com.learn2code.Shop.domain.Product;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
// Testy pomocou ktorych vytvorim nove data do troch tabuliek (Cust,Merch,Prod)
// Otestovanie funkcnosti DB modelu
// Namapovanie enitity na DB tabulku

@RunWith(SpringRunner.class)
@SpringBootTest // Tieto Anotacie docielia ze - tieto testy sa spustaju pod Spring kontextom
        // (ako keby bola appka spustena, a mozme vyuzivat vsetky beany)

// Dirty solution.. Zarucime aby pred kazdou testovaciou triedou bola vycistena H2 Databaza
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)

public class DBInsertTests {

    private final String insertCustomer =
            "INSERT INTO CUSTOMER(name, surname, email, address, age, phone_number) values(?, ?, ?, ?, ?, ?)";

    private final String insertMerchant =
            "INSERT INTO merchant(name, email, address) values(?, ?, ?)";

    private final String insertProduct =
            "INSERT INTO  product(merchant_id, name, description, price, created_at, available) values(?, ?, ?, ?, ?, ?)";

    @Autowired // nainjectuje beany (aby som mohol pouzivat napr jdbcTemplate)
    private JdbcTemplate jdbcTemplate;

    @Test
    public void createCustomer(){ // vyuzil som settre na inicializovanie jeho properties
        Customer customer = new Customer();
        customer.setName("Ferko");
        customer.setSurname("Mrkvicka");
        customer.setEmail("xxx");
        customer.setAddress("xxx");
        customer.setAge(16);
        customer.setPhoneNumber("420");

        jdbcTemplate.update(new PreparedStatementCreator() {
            // pomocou Prep.Stat. si nasetujeme values ?,?..

            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(insertCustomer);
                // con- db konekcia, insertCustomer - SQL command nad ktorym budeme replacovat tie parametre '?'
                ps.setString(1,customer.getName());
                ps.setString(2,customer.getSurname());
                ps.setString(3,customer.getEmail());
                ps.setString(4,customer.getAddress());
                ps.setInt(5,customer.getAge());
                ps.setString(6,customer.getPhoneNumber());
                return ps;
            }
        });
    }
    @Test
    public void createMerchant() {
        Merchant merchant = new Merchant();
        merchant.setName("merchantis");
        merchant.setEmail("bizniss.man@bizmail.biz");
        merchant.setAddress("blabolakova 18");

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(insertMerchant);
                ps.setString(1, merchant.getName());
                ps.setString(2, merchant.getEmail());
                ps.setString(3, merchant.getAddress());
                return ps;
            }
        });
    }

    @Test
    public void createProduct() {
        Product product = new Product();
        product.setMerchantId(1);
        product.setName("Keychron K3");
        product.setDescription("Low-profile");
        product.setPrice(75.5);
        product.setCreatedAt(Timestamp.from(Instant.now()));
        product.setAvailable(5);

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(insertProduct);
                ps.setInt(1, product.getMerchantId());
                ps.setString(2, product.getName());
                ps.setString(3, product.getDescription());
                ps.setDouble(4, product.getPrice());
                ps.setTimestamp(5, product.getCreatedAt());
                ps.setInt(6, product.getAvailable());
                return ps;
            }
        });
    }
}
