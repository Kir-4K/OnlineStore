package by.itacademy.dao;

import by.itacademy.entity.News;
import by.itacademy.entity.Role;
import by.itacademy.entity.User;
import org.junit.Test;

import java.time.LocalDateTime;

public class NewsDaoTest {

    private NewsDao newsDao = NewsDao.getInstance();

    @Test
    public void test() {
        News save = News.builder()
                .title("Тестовый заголовок")
                .text("Много тестового текста...")
                .date(LocalDateTime.now())
                .user(User.builder()
                        .login("Admin")
                        .password("ADMIN")
                        .role(Role.ADMIN)
                        .build())
                .build();
        newsDao.save(save);
        System.out.println(newsDao.findById(1L));

        News update = News.builder()
                .id(1L)
                .title("Новый тестовый заголовок")
                .text("Много нового тестового текста...")
                .date(LocalDateTime.now())
                .user(User.builder()
                        .id(1L)
                        .login("Admin")
                        .password("ADMIN")
                        .role(Role.ADMIN)
                        .build())
                .build();
        newsDao.update(update);
        System.out.println(newsDao.findById(1L));

        System.out.println(newsDao.findAll());

        News delete = News.builder()
                .id(1L)
                .title("")
                .text("")
                .date(LocalDateTime.now())
                .build();
        newsDao.delete(delete);
        System.out.println(newsDao.findById(1L));
    }
}
