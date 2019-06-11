package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.entity.QUser;
import by.itacademy.kostusev.entity.Role;
import by.itacademy.kostusev.entity.User;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.itacademy.kostusev.entity.Role.CUSTOMER;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        Optional<User> user = userRepository.findById(1L);
        user.ifPresent(value -> assertThat(value.getLogin(), equalTo("Admin")));
    }

    @Test
    public void testFindAll() {
        List<User> users = newArrayList(userRepository.findAll());
        assertThat(users, hasSize(3));
    }

    @Test
    public void testFindCurrentUser() {
        BooleanExpression expression = QUser.user.login.eq("Admin").and(QUser.user.password.eq("admin"));

        Optional<User> user = userRepository.findOne(expression);
        user.ifPresent(value -> assertThat(value.getLogin(), equalTo("Admin")));
    }

    @Test
    public void testGetAllCustomer() {
        BooleanExpression expression = QUser.user.role.eq(CUSTOMER);
        List<User> allCustomer = newArrayList(userRepository.findAll(expression));
        List<String> loginList = allCustomer.stream().map(User::getLogin).collect(toList());
        assertThat(loginList, hasSize(2));
        assertThat(loginList, contains("Karil", "Ivan"));
    }

    @Test
    public void testSave() {
        User save = getUser("Pavel", "pavel", CUSTOMER);
        userRepository.save(save);

        Optional<User> user = userRepository.findById(save.getId());
        user.ifPresent(value -> assertThat(value.getLogin(), equalTo("Pavel")));
    }

    private User getUser(String login, String password, Role role) {
        return User.builder()
                .login(login)
                .password(password)
                .role(role)
                .build();
    }

    @Test
    public void testUpdate() {
        userRepository.update(1L, "SuperAdmin", "admin");

        Optional<User> user = userRepository.findById(1L);
        user.ifPresent(value -> assertThat(value.getLogin(), equalTo("SuperAdmin")));
    }
}
