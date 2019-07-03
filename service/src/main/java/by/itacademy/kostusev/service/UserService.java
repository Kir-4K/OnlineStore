package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.UserDto;
import by.itacademy.kostusev.mapper.UserMapper;
import by.itacademy.kostusev.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findById(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

    public UserDto findByUsername(String name) {
        return Optional.ofNullable(userRepository.findByUsername(name))
                .map(userMapper::toDto)
                .orElse(null);
    }

    public UserDto getUserFromSession(Principal principal) {
        return Optional.ofNullable(userRepository.findByUsername(principal.getName()))
                .map(userMapper::toDto)
                .orElse(null);
    }

    public List<UserDto> findAll() {
        return newArrayList(userRepository.findAll())
                .stream()
                .map(userMapper::toDto)
                .collect(toList());
    }

    @Transactional
    public void saveOrUpdateUser(UserDto dto) {
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
