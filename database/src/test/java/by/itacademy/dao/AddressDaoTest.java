package by.itacademy.dao;

import by.itacademy.entity.Address;
import by.itacademy.util.ConnectionManager;
import by.itacademy.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class AddressDaoTest {

    private static SessionFactory sessionFactory = ConnectionManager.getFactory();
    private AddressDao addressDao = AddressDao.getInstance();

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

            Optional<Address> address = addressDao.findById(session, 1L);
            assertThat(address.get().getCity(), equalTo("Минск"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testSave() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Address save = Address.builder()
                    .city("Могилев")
                    .street("Димитрова")
                    .house("22")
                    .apartment("19")
                    .build();
            session.save(save);

            Optional<Address> address = addressDao.findById(session, save.getId());
            assertThat(address.get().getApartment(), equalTo("19"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Address update = Address.builder()
                    .id(1L)
                    .city("Минск")
                    .street("Заводская")
                    .house("12")
                    .apartment("35")
                    .build();
            session.update(update);

            Optional<Address> address = addressDao.findById(session, update.getId());
            assertThat(address.get().getApartment(), equalTo("35"));

            session.getTransaction().commit();
        }
    }
}
