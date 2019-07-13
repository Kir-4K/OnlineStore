package by.itacademy.kostusev.service;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.dto.AddressDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Test
    public void findByCustomersPhone() {
        AddressDto address = addressService.findByCustomersPhone("80291112221");
        assertThat(address.getId(), equalTo(1L));
    }

    @Test
    public void saveOrUpdateAddress() {
        AddressDto save = AddressDto.builder()
                .apartment("10")
                .build();

        addressService.saveOrUpdateAddress(save);

        AddressDto address = addressService.findById(3L);
        assertThat(address.getApartment(), equalTo("10"));
    }
}
