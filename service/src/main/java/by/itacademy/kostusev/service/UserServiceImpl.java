package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.UserDto;
import by.itacademy.kostusev.entity.User;
import by.itacademy.kostusev.mapper.UserMapper;
import by.itacademy.kostusev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent() ? userMapper.toDto(user.get()) : UserDto.builder().build();
    }

    public List<UserDto> findAll() {
        List<User> users = newArrayList(userRepository.findAll());
        return users.stream().map(userMapper::toDto).collect(toList());
    }

    public void registerNewUserAccount(UserDto dto) {
        userRepository.save(userMapper.toEntity(dto));
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return Optional.of(name)
                .map(userRepository::findByUsername)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(user.getRole().toString())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
