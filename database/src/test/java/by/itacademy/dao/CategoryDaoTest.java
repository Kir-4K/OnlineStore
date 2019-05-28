package by.itacademy.dao;

import by.itacademy.entity.Category;
import by.itacademy.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class CategoryDaoTest {

    private CategoryDao categoryDao = CategoryDao.getInstance();
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

            Category category = categoryDao.findById(session, 1L);
            assertThat(category.getName(), equalTo("Зелья"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testSave() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Category save = Category.builder()
                    .name("Прочее")
                    .build();
            session.save(save);

            Category category = categoryDao.findById(session, save.getId());
            assertThat(category.getName(), equalTo("Прочее"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Category update = Category.builder()
                    .id(1L)
                    .name("Зелье")
                    .build();
            session.update(update);

            Category category = categoryDao.findById(session, update.getId());
            assertThat(category.getName(), equalTo("Зелье"));

            session.getTransaction().commit();
        }
    }
}
