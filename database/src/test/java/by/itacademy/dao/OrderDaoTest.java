package by.itacademy.dao;

import by.itacademy.dto.FilterDto;
import by.itacademy.entity.Customer;
import by.itacademy.entity.Order;
import by.itacademy.entity.Status;
import by.itacademy.util.ConnectionManager;
import by.itacademy.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static by.itacademy.entity.Payment.CASH;
import static by.itacademy.entity.QOrder.order;
import static by.itacademy.entity.Status.PROCESSED;
import static by.itacademy.entity.Status.UNPROCESSED;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class OrderDaoTest {

    private static SessionFactory sessionFactory = ConnectionManager.getFactory();
    private OrderDao orderDao = OrderDao.getInstance();
    private CustomerDao customerDao = CustomerDao.getInstance();

    @BeforeClass
    public static void initDb() {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        TestDataImporter.getInstance().importTestData(sessionFactory);
    }

    @AfterClass
    public static void finish() {
        sessionFactory.close();
    }

    @Test
    public void testFindById() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Optional<Order> order = orderDao.findById(session, 1L);
            assertThat(order.get().getCustomer().getFirstName(), equalTo("Иван"));
            assertThat(order.get().getStatus(), equalTo(UNPROCESSED));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Order> orders = orderDao.findAll(session);
            assertThat(orders, hasSize(2));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testGetOrderByStatus() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            FilterDto filter = FilterDto.builder()
                    .predicates(order.status.eq(UNPROCESSED))
                    .build();

            List<Order> orders = orderDao.findAll(session, filter);
            List<Status> list = orders.stream().map(Order::getStatus).collect(toList());
            assertThat(orders, hasSize(2));
            assertThat(list, contains(UNPROCESSED, UNPROCESSED));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testSave() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Optional<Customer> customer = customerDao.findById(session, 1L);
            Order save = Order.builder()
                    .payment(CASH)
                    .date(LocalDateTime.now())
                    .status(UNPROCESSED)
                    .customer(customer.get())
                    .build();
            session.save(save);

            Optional<Order> order = orderDao.findById(session, save.getId());
            assertThat(order.get().getCustomer().getPhone(), equalTo("80(44)125-44-61"));
            assertThat(order.get().getPayment(), equalTo(CASH));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Order update = Order.builder()
                    .id(1L)
                    .payment(CASH)
                    .date(LocalDateTime.now())
                    .status(PROCESSED)
                    .build();
            session.update(update);

            Optional<Order> order = orderDao.findById(session, update.getId());
            assertThat(order.get().getCustomer(), nullValue());
            assertThat(order.get().getStatus(), equalTo(PROCESSED));

            session.getTransaction().commit();
        }
    }
}
