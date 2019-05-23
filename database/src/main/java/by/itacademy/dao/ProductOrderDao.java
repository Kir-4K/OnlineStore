package by.itacademy.dao;

import by.itacademy.entity.ProductOrder;
import by.itacademy.entity.ProductOrderPK;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOrderDao {

    private static final ProductOrderDao INSTANCE = new ProductOrderDao();

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    public ProductOrder findById(ProductOrderPK id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(ProductOrder.class, id);
        }
    }

    public List<ProductOrder> findAll() {
        try (Session session = FACTORY.openSession()) {
            return session.
                    createQuery("SELECT po FROM ProductOrder po", ProductOrder.class)
                    .list();
        }
    }

    public void save(ProductOrder productOrder) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.save(productOrder);
            session.getTransaction().commit();
        }
    }

    public void update(ProductOrder productOrder) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.update(productOrder);
            session.getTransaction().commit();
        }
    }

    public void delete(ProductOrder productOrder) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.delete(productOrder);
            session.getTransaction().commit();
        }
    }

    public static ProductOrderDao getInstance() {
        return INSTANCE;
    }
}
