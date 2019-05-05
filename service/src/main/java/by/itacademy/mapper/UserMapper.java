package by.itacademy.mapper;

import by.itacademy.dto.UserDto;
import by.itacademy.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper implements BaseMapper<User, UserDto> {

    private static final UserMapper INSTANCE = new UserMapper();

    @Override
    public UserDto mapToDto(User entity) {
        return UserDto.builder()
                .login(entity.getLogin())
                .password(entity.getPassword())
                .build();
    }

    @Override
    public User mapToEntity(UserDto dto) {
        return User.builder()
                .login(dto.getLogin())
                .password(dto.getPassword())
                .role("customer")
                .build();
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }
}
