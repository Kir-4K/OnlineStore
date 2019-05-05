package by.itacademy.service;

import by.itacademy.dao.UserDao;
import by.itacademy.dto.UserDto;
import by.itacademy.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserService {

    private static final UserService INSTANCE = new UserService();
    private UserDao userDao = UserDao.getInstance();
    private UserMapper userMapper = UserMapper.getInstance();

    public List<UserDto> findAll() {
        return userDao.findAll()
                .stream()
                .map(userMapper::mapToDto)
                .collect(Collectors.toList());
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
