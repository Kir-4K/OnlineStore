package by.itacademy.dao;

import by.itacademy.entity.Order;
import by.itacademy.entity.Product;
import by.itacademy.entity.ProductOrder;
import by.itacademy.entity.ProductOrderPK;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

public class ProductOrderDaoTest {

    private static SessionFactory sessionFactory = ConnectionManager.getFactory();
    private ProductOrderDao productOrderDao = ProductOrderDao.getInstance();
    private OrderDao orderDao = OrderDao.getInstance();
    private ProductDao productDao = ProductDao.getInstance();

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

            Long orderId = 1L;
            Long productId = 1L;

            ProductOrderPK pk = getProductOrderPK(session, orderId, productId);
            Optional<ProductOrder> productOrder = productOrderDao.findById(session, pk);
            assertThat(productOrder.get().getId().getOrder().getId(), equalTo(orderId));
            assertThat(productOrder.get().getId().getProduct().getId(), equalTo(productId));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<ProductOrder> productOrders = productOrderDao.findAll(session);
            assertThat(productOrders, hasSize(3));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testSave() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Long orderId = 2L;
            Long productId = 1L;
            Integer quantity = 3;

            ProductOrderPK pk = getProductOrderPK(session, orderId, productId);
            ProductOrder save = getProductOrder(pk, quantity);
            session.save(save);

            Optional<ProductOrder> productOrder = productOrderDao.findById(session, save.getId());
            assertThat(productOrder.get().getQuantity(), equalTo(quantity));
            assertThat(productOrder.get().getId().getProduct().getName(), equalTo("Святая вода"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Long orderId = 2L;
            Long productId = 1L;
            Integer quantity = 2;

            ProductOrderPK pk = getProductOrderPK(session, orderId, productId);
            ProductOrder update = getProductOrder(pk, quantity);
            session.update(update);

            Optional<ProductOrder> productOrder = productOrderDao.findById(session, update.getId());
            assertThat(productOrder.get().getQuantity(), equalTo(2));
            assertThat(productOrder.get().getId().getProduct().getName(), equalTo("Святая вода"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testDelete() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            Long orderId = 1L;
            Long productId = 1L;
            Integer quantity = 5;

            ProductOrderPK pk = getProductOrderPK(session, orderId, productId);
            ProductOrder delete = getProductOrder(pk, quantity);
            session.delete(delete);

            Optional<ProductOrder> productOrder = productOrderDao.findById(session, delete.getId());
            assertThat(productOrder.isPresent(), is(false));

            session.getTransaction().commit();
        }
    }

    private ProductOrder getProductOrder(ProductOrderPK pk, Integer quantity) {
        return ProductOrder.builder()
                .id(pk)
                .quantity(quantity)
                .build();
    }

    private ProductOrderPK getProductOrderPK(Session session, Long orderId, Long productId) {
        Optional<Order> order = orderDao.findById(session, orderId);
        Optional<Product> product = productDao.findById(session, productId);
        return ProductOrderPK.builder()
                .order(order.get())
                .product(product.get())
                .build();
    }
}
