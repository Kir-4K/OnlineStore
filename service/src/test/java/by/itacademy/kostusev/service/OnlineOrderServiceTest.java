package by.itacademy.kostusev.service;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.dto.OnlineOrderDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
public class OnlineOrderServiceTest {

    @Autowired
    private OnlineOrderService onlineOrderService;

    @Test
    public void findById() {
        OnlineOrderDto order = onlineOrderService.findById(1L);
        assertThat(order.getId(), equalTo(1L));
    }

    @Test
    public void saveOrUpdateOrder() {
        OnlineOrderDto save = OnlineOrderDto.builder()
                .date(LocalDateTime.now())
                .build();

        onlineOrderService.saveOrUpdateOrder(save);

        OnlineOrderDto order = onlineOrderService.findById(4L);
        assertThat(order.getId(), equalTo(4L));
    }
}
