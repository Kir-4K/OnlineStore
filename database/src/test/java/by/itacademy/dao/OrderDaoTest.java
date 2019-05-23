package by.itacademy.dao;

import by.itacademy.entity.Address;
import by.itacademy.entity.Order;
import by.itacademy.entity.OrderData;
import by.itacademy.entity.Payment;
import by.itacademy.entity.Role;
import by.itacademy.entity.Status;
import by.itacademy.entity.User;
import org.junit.Test;

import java.time.LocalDateTime;

public class OrderDaoTest {

    private OrderDao orderDao = OrderDao.getInstance();

    @Test
    public void test() {
        Order save = Order.builder()
                .status(Status.UNPROCESSED)
                .payment(Payment.CARD)
                .date(LocalDateTime.now())
                .customer(OrderData.builder()
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
                        .build())
                .build();
        orderDao.save(save);
        System.out.println(orderDao.findById(1L));

        Order update = Order.builder()
                .id(1L)
                .status(Status.UNPROCESSED)
                .payment(Payment.CARD)
                .date(LocalDateTime.now())
                .customer(OrderData.builder()
                        .id(1L)
                        .firstName("Иван")
                        .phone("80441235521")
                        .address(Address.builder()
                                .id(1L)
                                .build())
                        .user(User.builder()
                                .id(1L)
                                .login("ivan")
                                .password("ivan")
                                .role(Role.CUSTOMER)
                                .build())
                        .build())
                .build();
        orderDao.update(update);
        System.out.println(orderDao.findById(1L));

        System.out.println(orderDao.findAll());

        Order delete = Order.builder()
                .id(1L)
                .date(LocalDateTime.now())
                .customer(OrderData.builder()
                        .id(1L)
                        .firstName("Иван")
                        .phone("80441235521")
                        .address(Address.builder()
                                .id(1L)
                                .build())
                        .user(User.builder()
                                .id(1L)
                                .login("ivan")
                                .password("ivan")
                                .role(Role.CUSTOMER)
                                .build())
                        .build())
                .build();
        orderDao.delete(delete);
        System.out.println(orderDao.findById(1L));
    }
}
