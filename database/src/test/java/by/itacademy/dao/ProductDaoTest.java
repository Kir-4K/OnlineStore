package by.itacademy.dao;

import by.itacademy.entity.Category;
import by.itacademy.entity.Product;
import by.itacademy.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ProductDaoTest {

    private ProductDao productDao = ProductDao.getInstance();
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

            Product product = productDao.findById(session, 1L);
            assertThat(product.getName(), equalTo("Святая вода"));
            assertThat(product.getId(), equalTo(1L));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<Product> products = productDao.findAll(session);
            List<String> list = products.stream().map(Product::getName).collect(toList());
            assertThat(products, hasSize(3));
            assertThat(list, containsInAnyOrder("Святая вода", "Кровь утопца", "Весельчак"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindByCategory() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Category category = categoryDao.findById(session, 1L);
            List<Product> products = productDao.findByCategory(session, category);
            List<String> list = products.stream().map(Product::getName).collect(toList());
            assertThat(products, hasSize(3));
            assertThat(list, containsInAnyOrder("Святая вода", "Весельчак", "Ласточка"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testSave() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Category category = categoryDao.findById(session, 1L);
            Product save = Product.builder()
                    .name("Ласточка")
                    .price(24.50)
                    .number(12)
                    .rating(5.0)
                    .description("Великолепное зелье от всех возможных болезней!")
                    .category(category)
                    .build();
            session.save(save);

            Product product = productDao.findById(session, save.getId());
            assertThat(product.getName(), equalTo("Ласточка"));
            assertThat(product.getCategory().getName(), equalTo("Зелья"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Category category = categoryDao.findById(session, 1L);
            Product update = Product.builder()
                    .id(1L)
                    .name("Святая вода (Новинка!)")
                    .price(29.25)
                    .number(3)
                    .rating(4.5)
                    .description("Может, убить оборотня или вампира и не сможет, но Ваш организм точно прочистит.")
                    .category(category)
                    .build();
            session.update(update);

            Product product = productDao.findById(session, update.getId());
            assertThat(product.getName(), equalTo("Святая вода (Новинка!)"));
            assertThat(product.getCategory().getName(), equalTo("Зелья"));

            session.getTransaction().commit();
        }
    }
}
