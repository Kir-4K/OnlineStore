package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.entity.Category;
import by.itacademy.kostusev.entity.Product;
import by.itacademy.kostusev.entity.QProduct;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testFindById() {
        Optional<Product> product = productRepository.findById(1L);
        product.ifPresent(value -> assertThat(value.getName(), equalTo("Ласточка")));
    }

    @Test
    public void testFindAll() {
        List<Product> products = newArrayList(productRepository.findAll());
        List<String> list = products.stream().map(Product::getName).collect(toList());
        assertThat(products, hasSize(12));
        assertThat(list, containsInAnyOrder("Ласточка", "Весельчак", "Гром", "Иволга", "Зелье безумия",
                "Святая вода", "Кровь программиста", "Кровь утопца", "Пурга", "Шок-Жокей", "Поцелуй Дьявола", "Дикий мустанг"));
    }

    @Test
    public void testFindByCategory() {
        Optional<Category> category = categoryRepository.findById(2L);
        if (category.isPresent()) {
            BooleanExpression expression = QProduct.product.category.eq(category.get());

            ArrayList<Product> products = newArrayList(productRepository.findAll(expression));
            List<String> list = products.stream().map(Product::getName).collect(toList());
            assertThat(products, hasSize(2));
            assertThat(list, containsInAnyOrder("Кровь программиста", "Кровь утопца"));
        }
    }

    @Test
    public void testFindProductByPriceBetween() {
        BooleanExpression expression = QProduct.product.price.goe(0).and(QProduct.product.price.loe(10));

        List<Product> products = newArrayList(productRepository.findAll(expression));
        assertThat(products, hasSize(2));
    }

    @Test
    public void testFindAllOrderBy() {
        OrderSpecifier<Double> orderByPrice = QProduct.product.price.desc();
        OrderSpecifier<String> orderByName = QProduct.product.name.asc();

        List<Product> products = newArrayList(productRepository.findAll(orderByPrice, orderByName));
        List<String> list = products.stream().map(Product::getName).collect(toList());
        assertThat(list, contains("Зелье безумия", "Весельчак", "Шок-Жокей", "Ласточка", "Дикий мустанг", "Иволга",
                "Поцелуй Дьявола", "Пурга", "Святая вода", "Гром", "Кровь программиста", "Кровь утопца"));
    }

    @Test
    public void testFindLimitedProduct() {
        Integer page = 3;
        Integer size = 2;
        PageRequest pageRequest = PageRequest.of(page, size);

        List<Product> products = newArrayList(productRepository.findAll(pageRequest));
        System.out.println(products);
        List<String> list = products.stream().map(Product::getName).collect(toList());
        assertThat(products, hasSize(2));
        assertThat(list, contains("Пурга", "Дикий мустанг"));
    }

    @Test
    public void testSave() {
        Optional<Category> category = categoryRepository.findById(1L);
        if (category.isPresent()) {
            Product save = Product.builder()
                    .name("Тестовое Зелье")
                    .price(29.50)
                    .number(12)
                    .rating(5.0)
                    .description("Великолепное зелье от всех возможных болезней!")
                    .category(category.get())
                    .build();
            productRepository.save(save);

            Optional<Product> product = productRepository.findById(save.getId());
            product.ifPresent(value -> assertThat(value.getName(), equalTo("Тестовое Зелье")));
        }
    }

    @Test
    public void testUpdate() {
        Optional<Category> category = categoryRepository.findById(1L);
        if (category.isPresent()) {
            Product product = Product.builder()
                    .id(1L)
                    .name("Святая вода (Новинка!)")
                    .price(29.25)
                    .number(3)
                    .rating(4.5)
                    .description("Может, убить оборотня или вампира и не сможет, но Ваш организм точно прочистит.")
                    .category(category.get())
                    .build();
            productRepository.save(product);

            Optional<Product> productUpdate = productRepository.findById(1L);
            productUpdate.ifPresent(value -> assertThat(value.getName(), equalTo("Святая вода (Новинка!)")));
        }
    }
}
