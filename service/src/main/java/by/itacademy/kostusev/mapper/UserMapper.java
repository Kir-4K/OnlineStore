package by.itacademy.kostusev.mapper;

import by.itacademy.kostusev.dto.UserDto;
import by.itacademy.kostusev.entity.Role;
import by.itacademy.kostusev.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public UserDto toDto(User entity) {
        return UserDto.builder()
                .username(entity.getUsername())
                .password(entity.getPassword())
                .build();
    }

    public User toEntity(UserDto dto) {
        return User.builder()
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .role(Role.CUSTOMER)
                .build();
    }
}
