package by.itacademy.service;

import by.itacademy.dao.UserDao;
import by.itacademy.dto.UserDto;
import by.itacademy.entity.User;
import by.itacademy.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();
    private UserDao userDao = UserDao.getInstance();
    private UserMapper userMapper = UserMapper.getInstance();

    public User findById(Long id) {
        return userDao.findById(id);
    }

    public List<User> findAll() {
        return userDao.findAll();
    }

    public List<User> getAllCustomer() {
        return userDao.getAllCustomer();
    }

    public void save(UserDto dto) {
        userDao.save(userMapper.mapToEntity(dto));
    }

    public void delete(UserDto dto) {
        userDao.delete(userMapper.mapToEntity(dto));
    }

    public void update(UserDto dto) {
        userDao.update(userMapper.mapToEntity(dto));
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
