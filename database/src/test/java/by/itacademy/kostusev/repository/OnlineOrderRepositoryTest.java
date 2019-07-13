package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.Payment;
import by.itacademy.kostusev.entity.QOnlineOrder;
import by.itacademy.kostusev.entity.Status;
import com.google.common.collect.Lists;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.hamcrest.Matchers;
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
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
public class OnlineOrderRepositoryTest {

    @Autowired
    private OnlineOrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testFindById() {
        Optional<OnlineOrder> order = orderRepository.findById(2L);
        order.ifPresent(value -> assertThat(value.getCustomer().getFirstName(), equalTo("Иван")));
    }

    @Test
    public void testFindAll() {
        List<OnlineOrder> orders = Lists.newArrayList(orderRepository.findAll());
        assertThat(orders, hasSize(3));
    }

    @Test
    public void testGetOrderByStatus() {
        BooleanExpression expression = QOnlineOrder.onlineOrder.status.eq(Status.UNPROCESSED);
        List<OnlineOrder> orders = Lists.newArrayList(orderRepository.findAll(expression));
        List<Status> list = orders.stream().map(OnlineOrder::getStatus).collect(toList());
        assertThat(orders, hasSize(2));
        assertThat(list, Matchers.contains(Status.UNPROCESSED, Status.UNPROCESSED));
    }

    @Test
    public void testSave() {
        Optional<Customer> customer = customerRepository.findById(1L);
        if (customer.isPresent()) {
            OnlineOrder save = OnlineOrder.builder()
                    .payment(Payment.CASH)
                    .date(LocalDateTime.now())
                    .status(Status.UNPROCESSED)
                    .customer(customer.get())
                    .build();
            orderRepository.save(save);

            Optional<OnlineOrder> order = orderRepository.findById(save.getId());
            order.ifPresent(value -> assertThat(value.getCustomer().getPhone(), equalTo("80291112221")));
        }
    }

    @Test
    public void testUpdate() {
        Optional<OnlineOrder> order = orderRepository.findById(1L);
        if (order.isPresent()) {
            order.get().setPayment(Payment.CASH);
            order.get().setStatus(Status.ASSEMBLY);
            orderRepository.save(order.get());
        }

        Optional<OnlineOrder> orderUpdate = orderRepository.findById(1L);
        orderUpdate.ifPresent(value -> assertThat(value.getStatus(), equalTo(Status.ASSEMBLY)));
    }
}
