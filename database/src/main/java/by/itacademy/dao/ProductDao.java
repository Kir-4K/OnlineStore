package by.itacademy.dao;

import by.itacademy.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao {

    private static final ProductDao INSTANCE = new ProductDao();

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    public Product findById(Long id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(Product.class, id);
        }
    }

    public List<Product> findAll() {
        try (Session session = FACTORY.openSession()) {
            return session.
                    createQuery("SELECT p FROM Product p", Product.class)
                    .list();
        }
    }

    public List<Product> findByCategory(String category) {
        try (Session session = FACTORY.openSession()) {
            return session.
                    createQuery("SELECT p FROM Product p WHERE p.category.name =:category", Product.class)
                    .setParameter("category", category)
                    .list();
        }
    }

    public void save(Product product) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.save(product);
            session.getTransaction().commit();
        }
    }

    public void update(Product product) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
        }
    }

    public void delete(Product product) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        }
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
