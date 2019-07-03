package by.itacademy.kostusev.service;

import by.itacademy.kostusev.config.TestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
public class CartServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void addToCart() {

    }
}
