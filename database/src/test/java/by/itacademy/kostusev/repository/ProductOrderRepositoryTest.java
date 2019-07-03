package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.Product;
import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.entity.ProductOrderPK;
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

import static com.google.common.collect.Lists.newArrayList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class ProductOrderRepositoryTest {

    @Autowired
    private ProductOrderRepository productOrderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OnlineOrderRepository onlineOrderRepository;

    @Test
    public void testFindById() {
        Long orderId = 1L;
        Long productId = 7L;

        ProductOrderPK pk = getProductOrderPK(orderId, productId);
        Optional<ProductOrder> productOrder = productOrderRepository.findById(pk);
        productOrder.ifPresent(order -> assertThat(order.getId().getOrder().getId(), equalTo(orderId)));
    }

    @Test
    public void testFindAll() {
        List<ProductOrder> productOrders = newArrayList(productOrderRepository.findAll());
        assertThat(productOrders, hasSize(5));
    }

    @Test
    public void testSave() {
        Long orderId = 2L;
        Long productId = 1L;
        Integer quantity = 3;

        ProductOrderPK pk = getProductOrderPK(orderId, productId);
        ProductOrder save = getProductOrder(pk, quantity);
        productOrderRepository.save(save);

        Optional<ProductOrder> productOrder = productOrderRepository.findById(pk);
        productOrder.ifPresent(order -> assertThat(order.getId().getProduct().getName(), equalTo("Ласточка")));
    }

    @Test
    public void testUpdate() {
        Long orderId = 1L;
        Long productId = 7L;
        Integer quantity = 1;

        ProductOrderPK pk = getProductOrderPK(orderId, productId);
        Optional<ProductOrder> productOrder = productOrderRepository.findById(pk);

        if (productOrder.isPresent()) {
            productOrder.get().setQuantity(quantity);
            productOrderRepository.save(productOrder.get());
        }

        Optional<ProductOrder> productOrderUpdate = productOrderRepository.findById(pk);
        productOrderUpdate.ifPresent(order -> assertThat(order.getId().getProduct().getName(), equalTo("Кровь утопца")));
        productOrderUpdate.ifPresent(order -> assertThat(order.getQuantity(), equalTo(quantity)));
    }

    @Test
    public void testDelete() {
        Long orderId = 1L;
        Long productId = 7L;
        Integer quantity = 4;

        ProductOrderPK pk = getProductOrderPK(orderId, productId);
        ProductOrder delete = getProductOrder(pk, quantity);
        productOrderRepository.delete(delete);

        Optional<ProductOrder> productOrder = productOrderRepository.findById(pk);
        assertThat(productOrder.isPresent(), is(false));
    }

    private ProductOrder getProductOrder(ProductOrderPK pk, Integer quantity) {
        return ProductOrder.builder()
                .id(pk)
                .quantity(quantity)
                .build();
    }

    private ProductOrderPK getProductOrderPK(Long orderId, Long productId) {
        Optional<OnlineOrder> order = onlineOrderRepository.findById(orderId);
        Optional<Product> product = productRepository.findById(productId);
        return (order.isPresent() && product.isPresent()) ? ProductOrderPK.builder()
                .order(order.get())
                .product(product.get())
                .build()
                : ProductOrderPK.builder().build();
    }
}
