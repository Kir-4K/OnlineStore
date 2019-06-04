package by.itacademy.dao;

import by.itacademy.entity.QUser;
import by.itacademy.entity.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static by.itacademy.entity.QUser.user;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao implements BaseDao<Long, User>, BaseFilter<Long, User, QUser> {

    private static final UserDao INSTANCE = new UserDao();

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public QUser getQEntity() {
        return user;
    }
}
