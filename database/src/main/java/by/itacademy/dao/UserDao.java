package by.itacademy.dao;

import by.itacademy.entity.Role;
import by.itacademy.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    private static final SessionFactory FACTORY = new Configuration().configure().buildSessionFactory();

    public User findById(Long id) {
        try (Session session = FACTORY.openSession()) {
            return session.get(User.class, id);
        }
    }

    public User findCurrentUser(String login, String password) {
        try (Session session = FACTORY.openSession()) {
            return session.createQuery("SELECT u FROM User u WHERE u.login =:login AND u.password = :password", User.class)
                    .setParameter("login", login)
                    .setParameter("password", password)
                    .getSingleResult();
        }
    }

    public List<User> findAll() {
        try (Session session = FACTORY.openSession()) {
            return session.
                    createQuery("SELECT u FROM User u", User.class)
                    .list();
        }
    }

    public List<User> getAllCustomer() {
        try (Session session = FACTORY.openSession()) {
            return session
                    .createQuery("SELECT u FROM User u WHERE u.role =:userRole", User.class)
                    .setParameter("userRole", Role.CUSTOMER)
                    .list();
        }
    }

    public void save(User user) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
        }
    }

    public void update(User user) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.update(user);
            session.getTransaction().commit();
        }
    }

    public Integer updatePassword(User login, String newPassword) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            return session.createQuery("UPDATE User u SET u.password =:newPassword WHERE u.login =:login")
                    .setParameter("login", login.getLogin())
                    .setParameter("newPassword", newPassword)
                    .executeUpdate();
        }
    }

    public Integer updateLogin(User login, String newLogin) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            return session.createQuery("UPDATE User u SET u.login =:newLogin WHERE u.login =:login")
                    .setParameter("login", login.getLogin())
                    .setParameter("newLogin", newLogin)
                    .executeUpdate();
        }
    }

    public void delete(User user) {
        try (Session session = FACTORY.openSession()) {
            session.beginTransaction();
            session.delete(user);
            session.getTransaction().commit();
        }
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
