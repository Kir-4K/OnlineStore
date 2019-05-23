package by.itacademy.dao;

import by.itacademy.entity.Customer;
import by.itacademy.entity.OrderData;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDataDao {

    private static final OrderDataDao INSTANCE = new OrderDataDao();

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    public OrderData findById(Long id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(OrderData.class, id);
        }
    }

    public Customer findCustomerById(Long id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(Customer.class, id);
        }
    }

    public Integer updateCustomerType(Long login) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            return session.createQuery("UPDATE Customer c SET customer_type =:customerType WHERE c.id =:id")
                    .setParameter("id", login)
                    .setParameter("customerType", "REG")
                    .executeUpdate();
        }
    }

    public List<Customer> findAll() {
        try (Session session = FACTORY.openSession()) {
            return session.
                    createQuery("SELECT c FROM Customer c", Customer.class)
                    .list();
        }
    }

    public void save(OrderData customer) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.save(customer);
            session.getTransaction().commit();
        }
    }

    public void update(OrderData customer) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.update(customer);
            session.getTransaction().commit();
        }
    }

    public void delete(OrderData customer) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.delete(customer);
            session.getTransaction().commit();
        }
    }

    public static OrderDataDao getInstance() {
        return INSTANCE;
    }
}
