package by.itacademy.dao;

import by.itacademy.entity.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDao {

    private static final CategoryDao INSTANCE = new CategoryDao();

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    public Category findById(Long id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(Category.class, id);
        }
    }

    public void save(Category category) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.save(category);
            session.getTransaction().commit();
        }
    }

    public void update(Category category) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.update(category);
            session.getTransaction().commit();
        }
    }

    public void delete(Category category) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.delete(category);
            session.getTransaction().commit();
        }
    }

    public static CategoryDao getInstance() {
        return INSTANCE;
    }
}
