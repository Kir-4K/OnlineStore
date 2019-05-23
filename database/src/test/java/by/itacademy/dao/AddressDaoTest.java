package by.itacademy.dao;

import by.itacademy.entity.Address;
import org.junit.Test;

public class AddressDaoTest {

    private AddressDao addressDao = AddressDao.getInstance();

    @Test
    public void test() {
        Address save = Address.builder()
                .city("Минск")
                .street("Мира")
                .house("12")
                .apartment("22")
                .build();
        addressDao.save(save);
        System.out.println(addressDao.findById(1L));

        Address update = Address.builder()
                .id(1L)
                .city("Минск")
                .street("Пушкина")
                .house("22")
                .apartment("1")
                .build();
        addressDao.update(update);
        System.out.println(addressDao.findById(1L));

        Address delete = Address.builder()
                .id(1L)
                .build();
        addressDao.delete(delete);
        System.out.println(addressDao.findById(1L));
    }
}
