package by.itacademy.dao;

import by.itacademy.entity.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao {

    private static final OrderDao INSTANCE = new OrderDao();

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    public Order findById(Long id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(Order.class, id);
        }
    }

    public List<Order> findAll() {
        try (Session session = FACTORY.openSession()) {
            return session.
                    createQuery("SELECT o FROM Order o", Order.class)
                    .list();
        }
    }

    public void save(Order order) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.save(order);
            session.getTransaction().commit();
        }
    }

    public void update(Order order) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.update(order);
            session.getTransaction().commit();
        }
    }

    public void delete(Order order) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.delete(order);
            session.getTransaction().commit();
        }
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
