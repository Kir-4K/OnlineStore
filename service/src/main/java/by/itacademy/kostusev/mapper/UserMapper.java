package by.itacademy.kostusev.mapper;

import by.itacademy.kostusev.dto.UserDto;
import by.itacademy.kostusev.entity.Role;
import by.itacademy.kostusev.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User entity) {
        return UserDto.builder()
                .id(String.valueOf(entity.getId()))
                .login(entity.getLogin())
                .password(entity.getPassword())
                .build();
    }

    public User toEntity(UserDto dto) {
        return User.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .role(Role.CUSTOMER)
                .build();
    }
}
