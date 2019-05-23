package by.itacademy.dao;

import by.itacademy.entity.Address;
import by.itacademy.entity.OrderData;
import by.itacademy.entity.Role;
import by.itacademy.entity.User;
import org.junit.Test;

public class OrderDataDaoTest {

    private OrderDataDao orderDataDao = OrderDataDao.getInstance();

    @Test
    public void test() {
        OrderData save = OrderData.builder()
                .firstName("Кирилл")
                .phone("80441235521")
                .build();
        orderDataDao.save(save);
        System.out.println(orderDataDao.findById(1L));

        OrderData update = OrderData.builder()
                .id(1L)
                .firstName("Иван")
                .lastName("Иванов")
                .middleName("Иванович")
                .phone("80441235521")
                .mail("ivan@mail.ru")
                .address(Address.builder()
                        .city("Гомель")
                        .street("Богушевича")
                        .house("1")
                        .apartment("12")
                        .build())
                .user(User.builder()
                        .login("ivan")
                        .password("ivan")
                        .role(Role.CUSTOMER)
                        .build())
                .build();
        orderDataDao.update(update);
        orderDataDao.updateCustomerType(1L);
        System.out.println(orderDataDao.findById(1L));

        System.out.println(orderDataDao.findAll());

        System.out.println(orderDataDao.findCustomerById(1L));

        OrderData delete = OrderData.builder()
                .id(1L)
                .firstName("Иван")
                .phone("80441235521")
                .build();
        orderDataDao.delete(delete);
        System.out.println(orderDataDao.findById(1L));
    }
}
