package by.itacademy.kostusev.service;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.dto.UserDto;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void findById() {
        UserDto user = userService.findById(1L);
        Assert.assertThat(user.getUsername(), Matchers.equalTo("Admin"));
    }

    @Test
    public void findByUsername() {
        UserDto user = userService.findByUsername("Admin");
        Assert.assertThat(user.getUsername(), Matchers.equalTo("Admin"));
    }

    @Test
    public void getSessionUser() {
        Principal principal = () -> "Admin";
        UserDto user = userService.getSessionUser(principal);
        Assert.assertThat(user.getUsername(), Matchers.equalTo("Admin"));
    }

    @Test
    public void findAll() {
        List<UserDto> users = userService.findAll();
        Assert.assertThat(users, Matchers.hasSize(3));
    }

    @Test
    public void saveOrUpdate() {
        UserDto save = UserDto.builder()
                .username("Anna")
                .password("anna")
                .build();

        userService.saveOrUpdate(save);

        UserDto user = userService.findByUsername("Anna");
        Assert.assertThat(user.getUsername(), Matchers.equalTo("Anna"));
    }
}
