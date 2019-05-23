package by.itacademy.dao;

import by.itacademy.entity.Category;
import by.itacademy.entity.Order;
import by.itacademy.entity.OrderData;
import by.itacademy.entity.Payment;
import by.itacademy.entity.Product;
import by.itacademy.entity.ProductOrder;
import by.itacademy.entity.ProductOrderPK;
import by.itacademy.entity.Status;
import org.junit.Test;

import java.time.LocalDateTime;

public class ProductOrderDaoTest {

    private ProductOrderDao productOrderDao = ProductOrderDao.getInstance();

    @Test
    public void test() {
        ProductOrder save = ProductOrder.builder()
                .id(ProductOrderPK.builder()
                        .productId(1L)
                        .orderId(1L)
                        .build())
                .product(Product.builder()
                        .name("Зелье безумия")
                        .category(Category.builder()
                                .name("Зелье")
                                .build())
                        .price(12.20)
                        .description("Описание товара")
                        .build())
                .order(Order.builder()
                        .customer(OrderData.builder()
                                .firstName("TestName")
                                .phone("Test101Phone")
                                .build())
                        .date(LocalDateTime.now())
                        .payment(Payment.CARD)
                        .status(Status.UNPROCESSED)
                        .build())
                .quantity(2)
                .build();
        productOrderDao.save(save);
        System.out.println(productOrderDao.findById(ProductOrderPK.builder()
                .orderId(1L)
                .productId(1L)
                .build()));

        System.out.println(productOrderDao.findAll());

        ProductOrder update = ProductOrder.builder()
                .id(ProductOrderPK.builder()
                        .productId(1L)
                        .orderId(1L)
                        .build())
                .product(Product.builder()
                        .id(1L)
                        .name("Зелье безумия")
                        .category(Category.builder()
                                .id(1L)
                                .name("Зелье")
                                .build())
                        .price(12.20)
                        .description("Описание товара")
                        .build())
                .order(Order.builder()
                        .id(1L)
                        .customer(OrderData.builder()
                                .id(1L)
                                .firstName("TestName")
                                .phone("Test101Phone")
                                .build())
                        .date(LocalDateTime.now())
                        .payment(Payment.CARD)
                        .status(Status.UNPROCESSED)
                        .build())
                .quantity(5)
                .build();
        productOrderDao.update(update);
        System.out.println(productOrderDao.findById(ProductOrderPK.builder()
                .orderId(1L)
                .productId(1L)
                .build()));

        ProductOrder delete = ProductOrder.builder()
                .id(ProductOrderPK.builder()
                        .productId(1L)
                        .orderId(1L)
                        .build())
                .product(Product.builder()
                        .id(1L)
                        .name("Зелье безумия")
                        .category(Category.builder()
                                .id(1L)
                                .name("Зелье")
                                .build())
                        .price(12.20)
                        .description("Описание товара")
                        .build())
                .order(Order.builder()
                        .id(1L)
                        .customer(OrderData.builder()
                                .id(1L)
                                .firstName("TestName")
                                .phone("Test101Phone")
                                .build())
                        .date(LocalDateTime.now())
                        .payment(Payment.CARD)
                        .status(Status.UNPROCESSED)
                        .build())
                .quantity(5)
                .build();
        productOrderDao.delete(delete);
        System.out.println(productOrderDao.findById(ProductOrderPK.builder()
                .orderId(1L)
                .productId(1L)
                .build()));
    }
}
