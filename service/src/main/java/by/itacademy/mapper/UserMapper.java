package by.itacademy.mapper;

import by.itacademy.dto.UserDto;
import by.itacademy.entity.Role;
import by.itacademy.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {

    private static final UserMapper INSTANCE = new UserMapper();


    public UserDto mapToDto(User entity) {
        return UserDto.builder()
                .id(String.valueOf(entity.getId()))
                .login(entity.getLogin())
                .password(entity.getPassword())
                .build();
    }


    public User mapToEntity(UserDto dto) {
        return User.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .role(Role.CUSTOMER)
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
