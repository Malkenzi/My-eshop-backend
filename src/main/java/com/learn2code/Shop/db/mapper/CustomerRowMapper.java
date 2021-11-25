package com.learn2code.Shop.db.mapper;

import com.learn2code.Shop.domain.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

//Implementovanie databazoveho rozhrania
//mapuje blizsie nase entity na databazove entity
//mapuje jedlotlive riadky 'rows'

public class CustomerRowMapper implements RowMapper<Customer> { //RowMapper - Interface v JDBC balicku
    @Override
    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setName(rs.getString("name"));
        customer.setSurname(rs.getString("surname"));
        customer.setEmail(rs.getString("email"));
        customer.setAddress(rs.getString("address"));

        customer.setAge(rs.getObject("age") == null ? null : rs.getInt("age"));
        //ked je objekt v stlpci age null '?' tak si tam setneme 'null' ':' inak si setneme integer 'age'
        //ak by zakaznik nezadal vek tak by to bolo null (lenze primitiv.dat.typ nemoze byt null > preto 'Object')

        customer.setPhoneNumber(rs.getString("phone_number"));

        return customer;
    }
}
