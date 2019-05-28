package by.itacademy.service;

import by.itacademy.dao.UserDao;
import by.itacademy.dto.UserDto;
import by.itacademy.entity.User;
import by.itacademy.mapper.UserMapper;
import by.itacademy.util.ConnectionManager;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();
    private UserDao userDao = UserDao.getInstance();
    private UserMapper userMapper = UserMapper.getInstance();
    private Session session = ConnectionManager.getFactory().openSession();

    public User findById(Long id) {
        return userDao.findById(session, id);
    }

    public List<User> findAll() {
        return userDao.findAll(session);
    }

    public List<User> getAllCustomer() {
        return userDao.getAllCustomer(session);
    }

    public void save(UserDto dto) {
        session.save(userMapper.mapToEntity(dto));
    }

    public void delete(UserDto dto) {
        session.delete(userMapper.mapToEntity(dto));
    }

    public void update(UserDto dto) {
        session.update(userMapper.mapToEntity(dto));
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
