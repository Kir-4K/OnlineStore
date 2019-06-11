package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.UserDto;
import by.itacademy.kostusev.entity.User;
import by.itacademy.kostusev.mapper.UserMapper;
import by.itacademy.kostusev.repository.UserRepository;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent() ? userMapper.toDto(user.get()) : UserDto.builder().build();
    }

    public UserDto findCurrentUser(Predicate predicate) {
        Optional<User> user = userRepository.findOne(predicate);
        return user.isPresent() ? userMapper.toDto(user.get()) : UserDto.builder().build();
    }

    public List<UserDto> findAll() {
        List<User> users = newArrayList(userRepository.findAll());
        return users.stream().map(userMapper::toDto).collect(toList());
    }

    public List<UserDto> findAll(Predicate predicate, Pageable pageable) {
        List<User> users = newArrayList(userRepository.findAll(predicate, pageable));
        return users.stream().map(userMapper::toDto).collect(toList());
    }
}
