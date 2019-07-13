package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testFindById() {
        Optional<Address> address = addressRepository.findById(1L);
        address.ifPresent(value -> assertThat(value.getCity(), equalTo("Минск")));
    }

    @Test
    public void testFindByCustomerPhone() {
        Optional<Address> address = addressRepository.findByCustomersPhone("80291112221");
        address.ifPresent(value -> assertThat(value.getCity(), equalTo("Минск")));
    }

    @Test
    public void testSave() {
        Address save = Address.builder()
                .city("Могилев")
                .street("Димитрова")
                .house("22")
                .apartment("19")
                .build();
        addressRepository.save(save);

        Optional<Address> address = addressRepository.findById(save.getId());
        address.ifPresent(value -> assertThat(value.getApartment(), equalTo("19")));
    }

    @Test
    public void testUpdate() {
        Optional<Address> address = addressRepository.findById(1L);
        if (address.isPresent()) {
            address.get().setCity("Минск");
            address.get().setStreet("Заводская");
            address.get().setHouse("12");
            address.get().setApartment("35");
            addressRepository.save(address.get());
        }

        Optional<Address> addressUpdate = addressRepository.findById(1L);
        addressUpdate.ifPresent(value -> assertThat(value.getApartment(), equalTo("35")));
    }
}
