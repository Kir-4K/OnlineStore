package by.itacademy.dao;

import by.itacademy.entity.Category;
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
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class CategoryDaoTest {

    private static SessionFactory sessionFactory = ConnectionManager.getFactory();
    private CategoryDao categoryDao = CategoryDao.getInstance();

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

            Optional<Category> category = categoryDao.findById(session, 1L);
            assertThat(category.get().getName(), equalTo("Зелья"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Category> categories = categoryDao.findAll(session);
            List<String> list = categories.stream().map(Category::getName).collect(toList());
            assertThat(categories, hasSize(2));
            assertThat(list, contains("Зелья", "Ингредиенты"));

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

            Optional<Category> category = categoryDao.findById(session, save.getId());
            assertThat(category.get().getName(), equalTo("Прочее"));

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

            Optional<Category> category = categoryDao.findById(session, update.getId());
            assertThat(category.get().getName(), equalTo("Зелье"));

            session.getTransaction().commit();
        }
    }
}
