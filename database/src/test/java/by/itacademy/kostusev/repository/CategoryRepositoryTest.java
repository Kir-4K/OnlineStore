package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.config.TestConfig;
import by.itacademy.kostusev.entity.Category;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Sql("classpath:test_script.sql")
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testFindById() {
        Optional<Category> category = categoryRepository.findById(1L);
        category.ifPresent(value -> assertThat(value.getName(), equalTo("Зелья")));
    }

    @Test
    public void testFindAll() {
        List<Category> categories = newArrayList(categoryRepository.findAll());
        List<String> list = categories.stream().map(Category::getName).collect(toList());
        assertThat(categories, hasSize(2));
        assertThat(list, contains("Зелья", "Ингредиенты"));
    }

    @Test
    public void testSave() {
        Category save = Category.builder()
                .name("Прочее")
                .build();
        categoryRepository.save(save);

        Optional<Category> category = categoryRepository.findById(save.getId());
        category.ifPresent(value -> assertThat(value.getName(), equalTo("Прочее")));
    }

    @Test
    public void testUpdate() {
        Optional<Category> category = categoryRepository.findById(1L);
        if (category.isPresent()) {
            category.get().setName("Зелье");
            categoryRepository.save(category.get());
        }

        Optional<Category> categoryUpdate = categoryRepository.findById(1L);
        categoryUpdate.ifPresent(value -> assertThat(value.getName(), equalTo("Зелье")));
    }
}
