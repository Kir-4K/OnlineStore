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
import java.util.stream.Stream;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto findById(Long id) {
        return Stream.ofNullable(id)
                .map(userRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(userMapper::toDto)
                .findFirst()
                .orElse(null);
    }

    public UserDto findByUsername(String name) {
        return ofNullable(userRepository.findByUsername(name))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(userMapper::toDto)
                .orElse(null);
    }

    public UserDto getSessionUser(Principal principal) {
        return Stream.ofNullable(principal)
                .map(Principal::getName)
                .map(this::findByUsername)
                .findFirst()
                .orElse(null);
    }

    public List<UserDto> findAll() {
        return newArrayList(userRepository.findAll())
                .stream()
                .map(userMapper::toDto)
                .collect(toList());
    }

    @Transactional
    public void saveOrUpdate(UserDto dto) {
        userRepository.save(userMapper.toEntity(dto));
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        return of(name)
                .map(userRepository::findByUsername)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> org.springframework.security.core.userdetails.User.builder()
                        .username(user.getUsername())
                        .password(user.getPassword())
                        .authorities(user.getRole().toString())
                        .build())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
