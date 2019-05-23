package by.itacademy.dao;

import by.itacademy.entity.Category;
import by.itacademy.entity.Product;
import org.junit.Test;

public class ProductDaoTest {

    private ProductDao productDao = ProductDao.getInstance();

    @Test
    public void test() {
        Product save = Product.builder()
                .name("Зелье безумия Новое")
                .category(Category.builder()
                        .name("Зелье")
                        .build())
                .price(12.20)
                .description("Описание товара")
                .build();
        productDao.save(save);
        System.out.println(productDao.findById(1L));

        Product update = Product.builder()
                .id(1L)
                .name("Зелье безумия НОВИНКА")
                .category(Category.builder()
                        .id(1L)
                        .name("Зелье")
                        .build())
                .price(15.50)
                .number(5)
                .rating(5.0)
                .description("Описание товара НОВОЕ")
                .build();
        productDao.update(update);
        System.out.println(productDao.findById(1L));

        System.out.println(productDao.findAll());
        System.out.println(productDao.findByCategory("Зелье"));

        Product delete = Product.builder()
                .id(1L)
                .name("Зелье безумия НОВИНКА")
                .category(Category.builder()
                        .id(1L)
                        .name("")
                        .build())
                .price(15.50)
                .build();
        productDao.delete(delete);
        System.out.println(productDao.findById(1L));
    }
}
