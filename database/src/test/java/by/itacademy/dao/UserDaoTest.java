package by.itacademy.dao;

import by.itacademy.entity.Role;
import by.itacademy.entity.User;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

public class UserDaoTest {

    private UserDao userDao = UserDao.getInstance();

    @Test
    public void test() {
        User save = User.builder()
                .login("Anna")
                .password("anna")
                .role(Role.CUSTOMER)
                .build();
        userDao.save(save);
        assertThat(userDao.findById(1L), equalTo(save));

        User saveAdmin = User.builder()
                .login("Admin")
                .password("adminPass")
                .role(Role.ADMIN)
                .build();
        userDao.save(saveAdmin);
        assertThat(userDao.findAll(), contains(save, saveAdmin));

        List<User> role = userDao.getAllCustomer();
        assertThat(role, contains(save));

        User update = User.builder()
                .id(1L)
                .login("AnnaPro")
                .password("annaPro")
                .role(Role.CUSTOMER)
                .build();
        userDao.update(update);
        assertThat(userDao.findById(1L), equalTo(update));

        User delete = User.builder()
                .id(1L)
                .login("")
                .password("")
                .role(Role.CUSTOMER)
                .build();
        userDao.delete(delete);
        assertThat(userDao.findById(1L), nullValue());

        User currentUser = userDao.findCurrentUser("Admin", "adminPass");
        assertThat(currentUser, equalTo(saveAdmin));

        userDao.updatePassword(saveAdmin, "SuperAdmin");
        userDao.updateLogin(saveAdmin, "SuperAdmin");
        System.out.println(userDao.findById(2L));
    }
}
