package by.itacademy.dao;

import by.itacademy.entity.Role;
import by.itacademy.entity.User;
import by.itacademy.util.ConnectionManager;
import by.itacademy.util.TestDataImporter;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Optional;

import static by.itacademy.entity.QUser.user;
import static by.itacademy.entity.Role.ADMIN;
import static by.itacademy.entity.Role.CUSTOMER;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

public class UserDaoTest {

    private static SessionFactory sessionFactory = ConnectionManager.getFactory();
    private UserDao userDao = UserDao.getInstance();

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

            Optional<User> user = userDao.findById(session, 1L);
            assertThat(user.get().getLogin(), equalTo("Admin"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            List<User> users = userDao.findAll(session);
            assertThat(users, hasSize(3));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testFindCurrentUser() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            BooleanExpression expression = user.login.eq("SuperAdmin").and(user.password.eq("admin"));

            User user = userDao.findOne(session, expression);
            assertThat(user.getLogin(), equalTo("SuperAdmin"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testGetAllCustomer() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            BooleanExpression expression = user.role.eq(CUSTOMER);

            List<User> allCustomer = userDao.findAll(session, expression);
            List<String> loginList = allCustomer.stream().map(User::getLogin).collect(toList());
            assertThat(loginList, hasSize(3));
            assertThat(loginList, contains("Ivan", "Max", "Pavel"));

            session.getTransaction().commit();
        }
    }

    @Test
    public void testSave() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User save = getUser("Pavel", "pavel", CUSTOMER);
            session.save(save);

            Optional<User> user = userDao.findById(session, save.getId());
            assertThat(user.get().getLogin(), equalTo("Pavel"));

            session.getTransaction().commit();
        }
    }

    private User getUser(String login, String password, Role role) {
        return User.builder()
                .login(login)
                .password(password)
                .role(role)
                .build();
    }

    @Test
    public void testUpdate() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();

            User update = getUserById(1L, "SuperAdmin", "admin", ADMIN);
            session.update(update);

            Optional<User> user = userDao.findById(session, update.getId());
            assertThat(user.get().getLogin(), equalTo("SuperAdmin"));
            assertThat(user.get().getPassword(), equalTo("admin"));

            session.getTransaction().commit();
        }
    }

    private User getUserById(Long id, String login, String password, Role role) {
        return User.builder()
                .id(id)
                .login(login)
                .password(password)
                .role(role)
                .build();
    }
}
