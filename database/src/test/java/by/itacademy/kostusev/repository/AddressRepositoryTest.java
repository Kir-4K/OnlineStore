package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.entity.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    @Test
    public void testFindById() {
        Optional<Address> address = addressRepository.findById(1L);
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
        addressRepository.update(1L, "Минск", "Заводская", "12", "35");

        Optional<Address> address = addressRepository.findById(1L);
        address.ifPresent(value -> assertThat(value.getApartment(), equalTo("35")));
    }
}
