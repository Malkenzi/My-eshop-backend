package com.learn2code.Shop;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn2code.Shop.db.service.api.request.UpdateProductRequest;
import com.learn2code.Shop.domain.Customer;
import com.learn2code.Shop.domain.Merchant;
import com.learn2code.Shop.domain.Product;
import jdk.jfr.ContentType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.as;
import static org.assertj.core.api.BDDAssertions.and;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.web.servlet.function.RequestPredicates.contentType;

// Akceptacne testy ktorymi prevolavam endpointy

@RunWith(SpringRunner.class)
@SpringBootTest
// H2 - in memory DB

// Vycisti H2 Databazu pred kazdym spustenim
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@AutoConfigureMockMvc
public class RestControllerTests {
    // Controller tests
    // V tychto testoch budem simulovat klienta a volat shop aplikaciu
    // Chcem prevolat restovy endpoint ktorym zavolame pridanie noveho customera
    // nato budem potrebovat nieco ako MockMvc - Ktorym viem v tychto SpringBoot testoch volat nase REST metody


    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();
    //pomocou tejto triedy viem premenit obejkt customer na json objekt
    //.content(objectMapper.writeValueAsString(customer))

    private Merchant merchant; //Musime mat vytvoreneho Merchanta

    @Before
    // Prvy parameter konstruktora ktory ideme pouzit je Merchant ID
    // Kedze merchant neexisuje musime si ho vytvorit este pred pustenim testov
    public void createMerchant() throws Exception {
        if (merchant == null) {  //ak merchanta nemam inicializovaneho tak si ho vytvorim
            merchant = new Merchant("meno", "mail", "adr");

            String id = mockMvc.perform(post("/merchant")   //volam endpoint
                            .contentType(MediaType.APPLICATION_JSON) // posielam String reprez. Json merchanta
                            .content(objectMapper.writeValueAsString(merchant)))// pomocou objMap. prerobim na Json
                    .andExpect(status().isCreated()) // ocakavame 'created'
                    .andReturn().getResponse().getContentAsString(); // response as string id
            merchant.setId(objectMapper.readValue(id, Integer.class)); // pretyp. by objectMapper

        }
    }

    @Test
    public void product() throws Exception {
        Product product = new Product(merchant.getId(), "Logitech", "mechanical", 90, 5);

        // ADD product
        String id = mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(product)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();
        product.setId(Integer.valueOf(id));

        // GET product
        String productJson = mockMvc.perform(get("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        Product returnedProduct = objectMapper.readValue(productJson, Product.class);
        //pouzil som objectMapper nato aby som vrateny product v Json pretypovali na produkt(triedu)

        Assert.assertEquals(product, returnedProduct);

        // GET ALL products
        String listProductJson = mockMvc.perform(get("/product/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        List<Product> products = objectMapper.readValue(listProductJson, new TypeReference<List<Product>>() {
        });

        assert products.size() == 1;                    //velkost listu je 1
        Assert.assertEquals(product, products.get(0));
        // ten jeden je rovnaky s produktom ktory sme si v prvom rade vytvorili a pridali pomocou DB

        // UPDATE product
        double updatePrice = product.getPrice() + 1;
        int updateAvailable = product.getAvailable() + 5;
        UpdateProductRequest updateProductRequest =
                new UpdateProductRequest(product.getName(), product.getDescription(), updatePrice, updateAvailable);
        //meno som nezmenil, Desc. som nemzmenil, len cenu a dostupnost.

        //update casti produktu
        mockMvc.perform(patch("/product/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateProductRequest)))
                .andExpect(status().isOk()); //200 (podarilo sa)

        //Po update produktu si getnem updatnuty produkt
        String returnedUpdatedProduct = mockMvc.perform(get("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();

        //nova Instancia                        read        rUP              as    Prod.class
        Product updatedProduct = objectMapper.readValue(returnedUpdatedProduct,Product.class);

        //Test/kontrola zmeny hodnot ktore sme si hore inicializovali
        assert updatePrice == updatedProduct.getPrice();
        assert updateAvailable == updatedProduct.getAvailable();

        //4fun sout StringJson
        System.out.println(returnedUpdatedProduct);

        // DELETE product
        mockMvc.perform(delete("/product/" + product.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        //Tests
        //Delete test get = NotFound 404
        mockMvc.perform(get("/product/" + product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        //Delete test getall = size 0  :(
        String listJson = mockMvc.perform(get("/product/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        List<Product> products2 = objectMapper.readValue(listJson, new TypeReference<List<Product>>() {});

        assert products2.size() == 0;
    }

    @Test
    public void customer() throws Exception {           //Created customer method
        Customer customer = new Customer                //Add Customer
                ("Tommy", "Vercetti", "@mail", "Vice City", 21, "420");

        // ADD customer
        String id =
                mockMvc.perform(post("/customer")    // volam endpoint customer
                                .contentType(MediaType.APPLICATION_JSON) // posielam Json (header Json)
                                // do bodycka posielam nas customer (String reprezentaciu Json toho customera)
                                .content(objectMapper.writeValueAsString(customer)))//By objMapp prerobim > String(Json)
                        .andExpect(status().isCreated()) // ocakavame ze HttpStatus v response bude 'nejaky' Created
                        .andReturn().getResponse().getContentAsString(); // dostat odpoved a ulozit si ju do String id;
        //customer.setId(objectMapper.readValue(id, Integer.class)); //pretypovanie pomocou objectMapperu
        customer.setId(Integer.valueOf(id));

        // GET customer
        String customerJson = mockMvc.perform(get("/customer/" + customer.getId())
                        .contentType(MediaType.APPLICATION_JSON))   //potrebujeme posielat contentType(Json) mockMvc to vyzaduje
                .andExpect(status().isOk())                 //ocakavame odpoved
                .andReturn().getResponse().getContentAsString();    // vrati ak Json String customera

        //zo Stringu Json musime spravit objekt customer.class
        Customer returnedCustomer = objectMapper.readValue(customerJson, Customer.class);//read value cust.Json as C.Class

        //treba Otestovat/porovnat ci 'returnedCustomer' je rovnaky s 'customer'om
        Assert.assertEquals(customer, returnedCustomer);

        // GET ALL customers
        String listCustomersJson = mockMvc.perform(get("/customer")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        //read String listCustomersJson as List<Customer> customers
        List<Customer> customers = objectMapper.readValue(listCustomersJson, new TypeReference<List<Customer>>() {
        });

        //velkost vytvoreneho listu customerov je 1
        assert customers.size() == 1;//ked si getneme prveho(nulteho) customera

        //porovnavame customera(koteho sme pridali) s prvym z Listu customers parameter '0'
        Assert.assertEquals(customer, customers.get(0));
    }

    @Test
    public void merchant() throws Exception {
        // ADD merchant

        // merchant is already created (koli produktu ktory potrebuje pracovat s 'merchant_id')

        // GET merchant
        String merchantJson = mockMvc.perform(get("/merchant/" + merchant.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()) //200
                .andReturn().getResponse().getContentAsString();

        Merchant returnedMerchant = objectMapper.readValue(merchantJson, Merchant.class);
        Assert.assertEquals(merchant, returnedMerchant);

        // GET ALL merchants
        String listMerchJson =
                mockMvc.perform(get("/merchant")
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andReturn().getResponse().getContentAsString();

        List<Merchant> merchants = objectMapper.readValue(listMerchJson, new TypeReference<List<Merchant>>() {
        });

        assert merchants.size() == 1; //mame len jedneho merchanta
        Assert.assertEquals(merchant, merchants.get(0)); // nulty objekt je rovnaky s pridanym merch
        //'merchant' je (hore) vytvoreny objekt ktory sme poslali na vytvorenie (add)
        // druhy 'returnedMerchant' ktory sme pretypovali z merchantJson, a
        // merchantJson som dostal z odpovede na getnutie merchanta
    }
}
