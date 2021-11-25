package com.learn2code.Shop.db.repository;

import com.learn2code.Shop.db.mapper.MerchantRowMapper;
import com.learn2code.Shop.domain.Merchant;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@Component
public class MerchantRepository {
    private final JdbcTemplate jdbcTemplate;
    private final MerchantRowMapper merchantRowMapper = new MerchantRowMapper();


    public MerchantRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Merchant get(int id) {
        final String sql = "select * from merchant where merchant.id =" + id;
        try {
            return jdbcTemplate.queryForObject(sql, merchantRowMapper);
        } catch (EmptyResultDataAccessException e) { // ak nenajde ziaden row
            return null;
        }
    }

    public Integer add(Merchant merchant) {
        final String sql = "Insert into Merchant (name,email,address) values (?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                preparedStatement.setString(1, merchant.getName());
                preparedStatement.setString(2, merchant.getEmail());
                preparedStatement.setString(3, merchant.getAddress());
                return preparedStatement;
            }
        }, keyHolder); // second parameter
        if (keyHolder.getKey() != null) {
            return keyHolder.getKey().intValue();
        } else {
            return null;
        }
    }

    public List<Merchant> getAll() {
        final String sql = "select * from merchant";
        return jdbcTemplate.query(sql, merchantRowMapper);
    }
}

