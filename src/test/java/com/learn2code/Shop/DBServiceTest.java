package com.learn2code.Shop;

import com.learn2code.Shop.db.service.api.CustomerSerivce;
import com.learn2code.Shop.db.service.api.MerchantSerivce;
import com.learn2code.Shop.db.service.api.ProductSerivce;
import com.learn2code.Shop.db.service.api.request.UpdateProductRequest;
import com.learn2code.Shop.domain.Customer;
import com.learn2code.Shop.domain.Merchant;
import com.learn2code.Shop.domain.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

// Otestovanie DB modelu (databazovych rozhrani)
// Testovanie pridanie ziskanie editovanie a mazanie

@RunWith(SpringRunner.class)
@SpringBootTest

// Vycisti H2 Databazu pred kazdym spustenim
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)

public class DBServiceTest {
    //Autwired pouzivame v Testoch, a v PROD triedach pozuivame Constructor Injection
    @Autowired //aby som dostal do tejto premennej objekt customerService
    private CustomerSerivce customerSerivce;

    @Autowired
    private MerchantSerivce merchantSerivce;

    @Autowired
    private ProductSerivce productSerivce;

    // merchant property
    private Merchant merchant;
    // Na vytvorenie produktu musime mat u merchanta v databaze koli 'merchant.id'
    // V case pustenia testu 'Product' uz vieme ze sa pusti pomocna metoda ktora zaruci ze v DB mame nejakeho merchanta
    @Before //pomocna metoda (pustajuca sa pred kazdym testom)
    public void createMerchant() {
        if (merchant == null) {
            merchant = new Merchant("Merchname", "MerchMail", "Merchdress");
            Integer id = merchantSerivce.add(merchant);
            assert id != null;
            merchant.setId(id);
        }
    }


    @Test
    public void customer() {
        Customer customer = new Customer
                ("Ferko", "Mrkvicka", "emailTest", "Testova 88", 69, "420");
        // vytvorili sme si noveho customera

        //ADD
        Integer id = customerSerivce.add(customer); // id ktore mi funkcia add vratila si dam do 'id'
        assert id != null; // prvy sposob overenia ze sa nam to ulozilo do DB
        customer.setId(id); // id si setnem do customera    (kebyze to vymazem test neprejde)

        // GET 'ID' (na zaklade ID a setneme si ho do fromDB)
        Customer fromDB = customerSerivce.get(id);
        // overenie         //expected, with
        Assert.assertEquals(customer, fromDB);

        // GET ALL Customers
        List<Customer> customers = customerSerivce.getCustomers(); // getol som si vsetkych zakaznikov
        Assert.assertEquals(1, customers.size());//assertneme ze velkost listu je 1
        Assert.assertEquals(customer, customers.get(0));
    }

    @Test
    public void merchant() {
//        Merchant merchant = new Merchant("Merchname", "MerchMail", "Merchdress");
//        Integer id = merchantSerivce.add(merchant);
//        assert id != null;
//        merchant.setId(id);
        // Already created merchant in createMerchant function

        Merchant fromDB = merchantSerivce.get(merchant.getId()); // vdaka pomocnej triede
        Assert.assertEquals(merchant, fromDB);

        List<Merchant> merchants = merchantSerivce.getMerchants();
        Assert.assertEquals(1, merchants.size());
        Assert.assertEquals(merchant, merchants.get(0));
    }


    //V case pusenia tohto testu uz vieme ze sa pustila metoda @BEFORE ktora zaruci ze mame v databaze uz nejakeho Merchanta
    @Test
    public void product() { // vytvorili sme si novy produkt
        Product product = new Product(merchant.getId(), "ProduktName", "desc", 1999, 88);
        //potrebujem merchanta ktory je uz v databaze

        // ADD
        Integer id = productSerivce.add(product); // produkt som dal do databazy
        assert id != null; // assertol som si ze ID neni null, aby som overil ten insert
        product.setId(id); // to IDcko som setol k productu

        // GET ID
        Product fromDB = productSerivce.get(id);
        //getol som produkt databazy na zaklade ID, a ulozil do fromDB premennej

        Assert.assertEquals(product, fromDB);// porovnavame objekty

        // GET ALL
        List<Product> products = productSerivce.getProducts(); // getol som si vsetky produkty
        Assert.assertEquals(1, products.size());  // overil som si ze velkost listu je prave '1'
        Assert.assertEquals(product, products.get(0));// tiez sme si overili ze ten jeden produkt ktory mame, je totozny
                                                      // s tym nasim produktom ktory sme si vytvorili
        // UPD req
        product.setAvailable(10); // vytvorenie updateRequest
        UpdateProductRequest productRequest = new UpdateProductRequest
                (product.getName(), product.getDescription(), product.getPrice(), product.getAvailable());

        // UPDATE
        productSerivce.update(id,productRequest); // pouzitie updateRequestu
        Product fromDBAfterUpdate = productSerivce.get(id); // getli sme si produkt a dali sme ho do novej premennej
        Assert.assertEquals(product, fromDBAfterUpdate); // a assertli sme tieto dva produkty <
                                                        // ak su rovnake znamena (ze sa update podaril)
        Assert.assertNotEquals(fromDB,fromDBAfterUpdate);// kontrola ze ze tieto dve hodnoty niesu rovnake lebo
                                                        // fromDBAfterUpdate je obohateny o ten Available(10)

        // DELETE
        productSerivce.delete(id);  // Overenie Delete ze velkost getProducts ma size 0
        Assert.assertEquals(0,productSerivce.getProducts().size());


    }

}