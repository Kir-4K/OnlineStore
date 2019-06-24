package by.itacademy.kostusev.service;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.dto.ProductDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductService productService;

    @Test
    public void addToCart() {
        ProductDto productDto1 = productService.findById(1L);
        ProductDto productDto2 = productService.findById(2L);

        cartService.addToCart(productDto1);
//        cartService.addToCart(productDto1);
//        cartService.addToCart(productDto2);

        Map<ProductDto, Integer> cart1 = cartService.getCart();

        cartService.deleteFromCart(cart1, productDto1);
//        cartService.deleteFromCart(cart1, productDto2);

        cartService.getTotalSum(cart1);
        System.out.println(cart1);
    }
}
