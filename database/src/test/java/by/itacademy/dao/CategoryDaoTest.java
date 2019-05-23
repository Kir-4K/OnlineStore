package by.itacademy.dao;

import by.itacademy.entity.Category;
import org.junit.Test;

public class CategoryDaoTest {

    private CategoryDao categoryDao = CategoryDao.getInstance();

    @Test
    public void test() {
        Category save = Category.builder()
                .name("ЗельяТест")
                .build();
        categoryDao.save(save);
        System.out.println(categoryDao.findById(1L));

        Category update = Category.builder()
                .id(1L)
                .name("ИнгредиентыТест")
                .build();
        categoryDao.update(update);
        System.out.println(categoryDao.findById(1L));

        Category delete = Category.builder()
                .id(1L)
                .name("")
                .build();
        categoryDao.delete(delete);
        System.out.println(categoryDao.findById(1L));
    }
}
