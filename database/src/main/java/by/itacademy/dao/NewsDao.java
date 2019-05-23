package by.itacademy.dao;

import by.itacademy.entity.News;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewsDao {

    private static final NewsDao INSTANCE = new NewsDao();

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    public News findById(Long id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(News.class, id);
        }
    }

    public List<News> findAll() {
        try (Session session = FACTORY.openSession()) {
            return session.
                    createQuery("SELECT n FROM News n", News.class)
                    .list();
        }
    }

    public void save(News news) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.save(news);
            session.getTransaction().commit();
        }
    }

    public void update(News news) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.update(news);
            session.getTransaction().commit();
        }
    }

    public void delete(News news) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.delete(news);
            session.getTransaction().commit();
        }
    }

    public static NewsDao getInstance() {
        return INSTANCE;
    }
}
