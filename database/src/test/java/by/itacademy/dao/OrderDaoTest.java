package by.itacademy.dao;

import by.itacademy.entity.Customer;
import by.itacademy.entity.Order;
import by.itacademy.entity.Payment;
import by.itacademy.entity.Status;
import by.itacademy.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class OrderDaoTest {

    private OrderDao orderDao = OrderDao.getInstance();
    private CustomerDao customerDao = CustomerDao.getInstance();
    private static SessionFactory sessionFactory;

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

            Order order = orderDao.findById(session, 1L);
            assertThat(order.getCustomer().getFirstName(), equalTo("Иван"));
            assertThat(order.getStatus(), equalTo(Status.UNPROCESSED));

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
    public void testSave() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Customer customer = customerDao.findById(session, 1L);
            Order save = Order.builder()
                    .payment(Payment.CASH)
                    .date(LocalDateTime.now())
                    .status(Status.UNPROCESSED)
                    .customer(customer)
                    .build();
            session.save(save);

            Order order = orderDao.findById(session, save.getId());
            assertThat(order.getCustomer().getPhone(), equalTo("80(44)125-44-61"));
            assertThat(order.getPayment(), equalTo(Payment.CASH));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Order update = Order.builder()
                    .id(1L)
                    .payment(Payment.CASH)
                    .date(LocalDateTime.now())
                    .status(Status.PROCESSED)
                    .build();
            session.update(update);

            Order order = orderDao.findById(session, update.getId());
            assertThat(order.getCustomer(), nullValue());
            assertThat(order.getStatus(), equalTo(Status.PROCESSED));

            session.getTransaction().commit();
        }
    }
}
