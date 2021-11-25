package com.learn2code.Shop.db.mapper;

import com.learn2code.Shop.domain.BoughtProduct;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BoughtProductRowMapper implements RowMapper<BoughtProduct> {
    @Override
    //v tejto Metod potrebujem vytvorit novu instanciu boughtProduct
    //nasetovat jej fieldy na zaklade resultSetu
    //tymto rs sa viem dotykat stlpcov databazovej polozky boughtProduct lebo ten R.Mapper je zavezeny na<BoughtProduct>
    public BoughtProduct mapRow(ResultSet resultSet, int i) throws SQLException {
        BoughtProduct boughtProduct = new BoughtProduct();

        //Teraz musim setovat jednotlive veci co potrebujem na zaklade toho 'rs'
        boughtProduct.setProductId(resultSet.getInt("product_id"));
        boughtProduct.setProductId(resultSet.getInt("customer_id"));
        boughtProduct.setQuantity(resultSet.getInt("quantity"));
        boughtProduct.setBoughtAt(resultSet.getTimestamp("bought_at"));
        return boughtProduct;
    }
}
