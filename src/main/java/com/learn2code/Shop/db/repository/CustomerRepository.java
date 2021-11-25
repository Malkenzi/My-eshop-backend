package com.learn2code.Shop.db.repository;

// najspodnejsia vrstva
// komunikacia s databazou pomocou JDBC template
// Sluzi len na komunikaciu a nestara sa o logiku
// posielanie SQL skriptov

import com.learn2code.Shop.db.mapper.CustomerRowMapper;
import com.learn2code.Shop.domain.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.List;

@Component // aby sa vytvorila beana(objekt) CustomerRepository aby som to mohol pouzit v Servise impl
public class CustomerRepository {
    private final JdbcTemplate jdbcTemplate;
    private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

    public CustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Customer get(int id) {
        final String sql = "select * from customer where customer.id = " + id;
        try {
            return jdbcTemplate.queryForObject(sql, customerRowMapper); //ked chcem jeden objekt tak (queryForObject)
        }catch ( EmptyResultDataAccessException e) {                    //ked chceme viac tak pouzijeme (query)
            return  null;                                   // exception ktoru vyhodi ak nenajde ziaden row (id)
        }                                                   // mozem vratit null lebo id was not found
    }
    //pridanie
    public Integer add(Customer customer) {
        final String sql = "Insert into customer(name,surname,email,address,age,phone_number) values (?,?,?,?,?,?)";

        // KeyHolder pomocou ktoreho si vratim vygenerovane ID
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
                //ked mi Customera setne v databaze nech nam stihne vratit ID ktore vygenerovalo

                ps.setString(1, customer.getName());
                ps.setString(2,customer.getSurname());
                ps.setString(3,customer.getEmail());
                ps.setString(4, customer.getAddress());
                if(customer.getAge() != null) {
                    ps.setInt(5,customer.getAge());
                }else {
                    ps.setNull(5, Types.INTEGER); // ps.setInt ocakava primit.int a kedze je to Nullable
                }                                               // musime to dat na Referencny datovy typ
                ps.setString(6,customer.getPhoneNumber());
                return ps;
            }
        }, keyHolder); // second parameter
        //Ked uz prebehol cely PreparedStatement tak KeyHolder by mal obsahovat ten KEY (ked sa podaril insert)
        if(keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }
    //Funkcia na vratenie vsetkych customerov
    public List<Customer> getAll() {
        final String sql = "Select * from customer";
        return jdbcTemplate.query(sql, customerRowMapper);// ked dame query vrati nam 'list' objektov
                                                          //CustRowMap.- namapuje rows v databaze nanapuje
                                                          // a potom nam vrati uceleny list customerov
    }
}
// funkcia kde sme vyuzili sme update s key holderom ktory nam vrti IDcko
// Toto je sposob akym nam pri vytvoreni noveho riadku do tabulky to vygenerovane ID mozeme ziskat pomocu Key Holdra