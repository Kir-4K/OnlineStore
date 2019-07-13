package by.itacademy.kostusev.service;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.dto.CustomerDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    public void findByUsername() {
        CustomerDto customer = customerService.findByUsername("Max");
        assertThat(customer.getFirstName(), equalTo("Максим"));
    }

    @Test
    public void getSessionCustomer() {
        Principal principal = () -> "Max";
        CustomerDto customer = customerService.getSessionCustomer(principal);
        assertThat(customer.getFirstName(), equalTo("Максим"));
    }

    @Test
    public void findByPhone() {
        CustomerDto customer = customerService.findByPhone("80291112221");
        assertThat(customer.getFirstName(), equalTo("Максим"));
    }

    @Test
    public void saveOrUpdateCustomer() {
        CustomerDto save = CustomerDto.builder()
                .firstName("Bob")
                .phone("80291112233")
                .build();

        customerService.saveOrUpdateCustomer(save);

        CustomerDto customer = customerService.findByPhone("80291112233");
        assertThat(customer.getFirstName(), equalTo("Bob"));
    }
}
