package by.itacademy.dao;

import by.itacademy.entity.Role;
import by.itacademy.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    public User findById(Session session, Long id) {
        return session.get(User.class, id);
    }

    public User findCurrentUser(Session session, String login, String password) {
        return session.createQuery("SELECT u FROM User u WHERE u.login =:login AND u.password = :password", User.class)
                .setParameter("login", login)
                .setParameter("password", password)
                .getSingleResult();
    }

    public List<User> findAll(Session session) {
        return session.
                createQuery("SELECT u FROM User u", User.class)
                .list();
    }

    public List<User> getAllCustomer(Session session) {
        return session
                .createQuery("SELECT u FROM User u WHERE u.role =:userRole", User.class)
                .setParameter("userRole", Role.CUSTOMER)
                .list();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
