package by.itacademy.dao;

import by.itacademy.entity.Address;
import by.itacademy.entity.Customer;
import by.itacademy.entity.User;
import by.itacademy.util.ConnectionManager;
import by.itacademy.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class CustomerDaoTest {

    private static SessionFactory sessionFactory = ConnectionManager.getFactory();
    private CustomerDao customerDao = CustomerDao.getInstance();
    private AddressDao addressDao = AddressDao.getInstance();
    private UserDao userDao = UserDao.getInstance();

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

            Optional<Customer> customer = customerDao.findById(session, 1L);
            assertThat(customer.get().getMail(), equalTo("ivan@mail.com"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Customer> customers = customerDao.findAll(session);
            List<String> firstNameCustomerList = customers.stream().map(Customer::getFirstName).collect(toList());
            assertThat(firstNameCustomerList, contains("Иван", "Максим", "Павел"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testSaveSimplyCustomer() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Customer save = Customer.builder()
                    .firstName("Павел")
                    .phone("80(29)221-35-18")
                    .build();
            session.save(save);

            Optional<Customer> customer = customerDao.findById(session, save.getId());
            assertThat(customer.get().getPhone(), equalTo("80(29)221-35-18"));
            assertThat(customer.get().getMail(), nullValue());

            session.getTransaction().commit();
        }
    }

    @Test
    public void testSaveComplexCustomer() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Optional<User> user = userDao.findById(session, 1L);
            Optional<Address> address = addressDao.findById(session, 2L);
            Customer save = Customer.builder()
                    .firstName("Павел")
                    .lastName("Павлович")
                    .middleName("Александрович")
                    .phone("80(29)221-35-28")
                    .mail("pavel@mail.com")
                    .user(user.get())
                    .address(address.get())
                    .build();
            session.save(save);

            Optional<Customer> customer = customerDao.findById(session, save.getId());
            assertThat(customer.get().getPhone(), equalTo("80(29)221-35-28"));
            assertThat(customer.get().getUser().getLogin(), equalTo("Admin"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Customer update = Customer.builder()
                    .id(1L)
                    .firstName("Ольга")
                    .phone("80(29)221-35-35")
                    .mail("olga@mail.com")
                    .build();
            session.update(update);

            Optional<Customer> customer = customerDao.findById(session, update.getId());
            assertThat(customer.get().getMail(), equalTo("olga@mail.com"));
            assertThat(customer.get().getFirstName(), equalTo("Ольга"));

            session.getTransaction().commit();
        }
    }
}
