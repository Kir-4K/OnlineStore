package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.entity.News;
import by.itacademy.kostusev.entity.User;
import com.google.common.collect.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
public class NewsRepositoryTest {

    @Autowired
    private NewsRepository newsRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testFindById() {
        Optional<News> news = newsRepository.findById(1L);
        news.ifPresent(value -> assertThat(value.getUser().getUsername(), equalTo("Admin")));
    }

    @Test
    public void testFindAll() {
        List<News> news = Lists.newArrayList(newsRepository.findAll());
        List<String> users = news.stream().map(News::getUser).map(User::getUsername).collect(toList());
        assertThat(users, contains("Admin", "Admin"));
    }

    @Test
    public void testSave() {
        Optional<User> user = userRepository.findById(1L);
        if (user.isPresent()) {
            News save = News.builder()
                    .user(user.get())
                    .date(LocalDateTime.now())
                    .title("Новые новости")
                    .text("Отличные новсти, народ!")
                    .build();
            newsRepository.save(save);

            Optional<News> news = newsRepository.findById(save.getId());
            news.ifPresent(value -> assertThat(value.getUser().getUsername(), equalTo("Admin")));
        }
    }

    @Test
    public void testUpdate() {
        Optional<News> news = newsRepository.findById(1L);
        if (news.isPresent()) {
            news.get().setTitle("Новые новости (Обновлено)");
            news.get().setText("Отличные новсти, народ!");
            newsRepository.save(news.get());
        }

        Optional<News> newsUpdate = newsRepository.findById(1L);
        newsUpdate.ifPresent(value -> assertThat(value.getTitle(), equalTo("Новые новости (Обновлено)")));
    }
}
