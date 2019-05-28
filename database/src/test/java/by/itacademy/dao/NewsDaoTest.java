package by.itacademy.dao;

import by.itacademy.entity.News;
import by.itacademy.entity.User;
import by.itacademy.util.TestDataImporter;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class NewsDaoTest {

    private NewsDao newsDao = NewsDao.getInstance();
    private UserDao userDao = UserDao.getInstance();
    private static SessionFactory sessionFactory;

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

            News news = newsDao.findById(session, 1L);
            assertThat(news.getUser().getLogin(), equalTo("Admin"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<News> news = newsDao.findAll(session);
            List<String> users = news.stream().map(News::getUser).map(User::getLogin).collect(toList());
            assertThat(users, contains("Admin", "Admin"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testSave() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = userDao.findById(session, 1L);
            News save = News.builder()
                    .user(user)
                    .date(LocalDateTime.now())
                    .title("Новые новости")
                    .text("Отличные новсти, народ!")
                    .build();
            session.save(save);

            News news = newsDao.findById(session, save.getId());
            assertThat(news.getUser().getLogin(), equalTo("Admin"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User user = userDao.findById(session, 1L);
            News update = News.builder()
                    .id(1L)
                    .user(user)
                    .date(LocalDateTime.now())
                    .title("Новые новости (Обновлено)")
                    .text("Отличные новсти, народ!")
                    .build();
            session.update(update);

            News news = newsDao.findById(session, update.getId());
            assertThat(news.getTitle(), equalTo("Новые новости (Обновлено)"));

            session.getTransaction().commit();
        }
    }
}
