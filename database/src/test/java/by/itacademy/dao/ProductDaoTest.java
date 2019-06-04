package by.itacademy.dao;

import by.itacademy.dto.FilterDto;
import by.itacademy.dto.LimitOffsetDto;
import by.itacademy.entity.Category;
import by.itacademy.entity.Product;
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

import static by.itacademy.entity.QProduct.product;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class ProductDaoTest {

    private static SessionFactory sessionFactory = ConnectionManager.getFactory();
    private ProductDao productDao = ProductDao.getInstance();
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

            Optional<Product> product = productDao.findById(session, 1L);
            assertThat(product.get().getName(), equalTo("Святая вода"));
            assertThat(product.get().getId(), equalTo(1L));

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

            Optional<Category> category = categoryDao.findById(session, 1L);
            FilterDto filter = FilterDto.builder()
                    .predicates(product.category.eq(category.get()))
                    .build();

            List<Product> products = productDao.findAll(session, filter);
            List<String> list = products.stream().map(Product::getName).collect(toList());
            assertThat(products, hasSize(3));
            assertThat(list, containsInAnyOrder("Святая вода", "Весельчак", "Ласточка"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindProductByPriceBetween() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            FilterDto filter = FilterDto.builder()
                    .predicates(product.price.goe(0))
                    .predicates(product.price.loe(10))
                    .build();

            List<Product> products = productDao.findAll(session, filter);
            assertThat(products, hasSize(1));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAllOrderBy() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            FilterDto filter = FilterDto.builder()
                    .specifiers(product.name.desc())
                    .specifiers(product.price.desc())
                    .build();

            List<Product> products = productDao.findAll(session, filter);
            List<String> list = products.stream().map(Product::getName).collect(toList());
            assertThat(list, contains("Святая вода", "Весельчак", "Кровь утопца"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindLimitedProduct() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            FilterDto filter = FilterDto.builder()
                    .limitOffset(LimitOffsetDto.builder()
                            .limit(2L)
                            .offset(1L)
                            .build())
                    .build();

            List<Product> products = productDao.findAll(session, filter);
            List<String> list = products.stream().map(Product::getName).collect(toList());
            assertThat(products, hasSize(2));
            assertThat(list, contains("Весельчак", "Кровь утопца"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testSave() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Optional<Category> category = categoryDao.findById(session, 1L);
            Product save = Product.builder()
                    .name("Ласточка")
                    .price(29.50)
                    .number(12)
                    .rating(5.0)
                    .description("Великолепное зелье от всех возможных болезней!")
                    .category(category.get())
                    .build();
            session.save(save);

            Optional<Product> product = productDao.findById(session, save.getId());
            assertThat(product.get().getName(), equalTo("Ласточка"));
            assertThat(product.get().getCategory().getName(), equalTo("Зелья"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Optional<Category> category = categoryDao.findById(session, 1L);
            Product update = Product.builder()
                    .id(1L)
                    .name("Святая вода (Новинка!)")
                    .price(29.25)
                    .number(3)
                    .rating(4.5)
                    .description("Может, убить оборотня или вампира и не сможет, но Ваш организм точно прочистит.")
                    .category(category.get())
                    .build();
            session.update(update);

            Optional<Product> product = productDao.findById(session, update.getId());
            assertThat(product.get().getName(), equalTo("Святая вода (Новинка!)"));
            assertThat(product.get().getCategory().getName(), equalTo("Зелья"));

            session.getTransaction().commit();
        }
    }
}
