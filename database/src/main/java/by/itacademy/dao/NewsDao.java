package by.itacademy.dao;

import by.itacademy.entity.News;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class NewsDao {

    private static final NewsDao INSTANCE = new NewsDao();

    public News findById(Session session, Long id) {
        return session.get(News.class, id);
    }

    public List<News> findAll(Session session) {
        return session.
                createQuery("SELECT n FROM News n", News.class)
                .list();
    }

    public static NewsDao getInstance() {
        return INSTANCE;
    }
}
