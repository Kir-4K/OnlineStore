package by.itacademy.dao;

import by.itacademy.entity.Address;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AddressDao {

    private static final AddressDao INSTANCE = new AddressDao();

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    public Address findById(Long id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(Address.class, id);
        }
    }

    public void save(Address address) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.save(address);
            session.getTransaction().commit();
        }
    }

    public void update(Address address) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.update(address);
            session.getTransaction().commit();
        }
    }

    public void delete(Address address) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.delete(address);
            session.getTransaction().commit();
        }
    }

    public static AddressDao getInstance() {
        return INSTANCE;
    }
}
