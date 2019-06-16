package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.entity.Address;
import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.entity.User;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        Optional<Customer> customer = customerRepository.findById(1L);
        customer.ifPresent(value -> assertThat(value.getMail(), equalTo("max@mail.ru")));
    }

    @Test
    public void testFindAll() {
        List<Customer> customers = Lists.newArrayList(customerRepository.findAll());
        List<String> firstNameCustomerList = customers.stream().map(Customer::getFirstName).collect(toList());
        assertThat(firstNameCustomerList, containsInAnyOrder("Иван", "Максим", "Взяткер"));
    }

    @Test
    public void testSaveSimplyCustomer() {
        Customer save = Customer.builder()
                .firstName("Павел")
                .phone("80(29)221-35-18")
                .build();
        customerRepository.save(save);

        Optional<Customer> customer = customerRepository.findById(save.getId());
        customer.ifPresent(value -> assertThat(value.getPhone(), equalTo("80(29)221-35-18")));
    }

    @Test
    public void testSaveComplexCustomer() {
        Optional<User> user = userRepository.findById(1L);
        Optional<Address> address = addressRepository.findById(2L);
        if (user.isPresent() && address.isPresent()) {
            Customer save = Customer.builder()
                    .firstName("Павел")
                    .lastName("Павлович")
                    .middleName("Александрович")
                    .phone("80(29)221-35-28")
                    .mail("pavel@mail.com")
                    .user(user.get())
                    .address(address.get())
                    .build();
            customerRepository.save(save);

            Optional<Customer> customer = customerRepository.findById(save.getId());
            customer.ifPresent(value -> assertThat(value.getUser().getUsername(), equalTo("Admin")));
        }
    }

    @Test
    public void testUpdate() {
        customerRepository.update(1L, "Взяткер", "Позолот", "Богатович", "gold@mail.com", "80(29)221-35-35");

        Optional<Customer> customer = customerRepository.findById(1L);
        customer.ifPresent(value -> assertThat(value.getMail(), equalTo("gold@mail.com")));
        customer.ifPresent(value -> assertThat(value.getFirstName(), equalTo("Взяткер")));
    }
}
