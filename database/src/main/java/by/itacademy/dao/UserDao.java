package by.itacademy.dao;

import by.itacademy.entity.User;
import by.itacademy.utilDatabase.ConnectionPool;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDao {

    private static final UserDao INSTANCE = new UserDao();

    private static final String FIND_ALL = "SELECT * FROM online_store.user";

    @SneakyThrows
    public List<User> findAll() {
        List<User> users = new ArrayList<>();
        try (Connection connection = ConnectionPool.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(FIND_ALL)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                User fromResultSet = getResultSet(resultSet);
                users.add(fromResultSet);
            }
        }
        return users;
    }

    private by.itacademy.entity.User getResultSet(ResultSet resultSet) throws SQLException {
        return by.itacademy.entity.User.builder()
                .id(resultSet.getInt("id"))
                .login(resultSet.getString("user_login"))
                .password(resultSet.getString("user_pass"))
                .role(resultSet.getString("role"))
                .build();
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }
}
