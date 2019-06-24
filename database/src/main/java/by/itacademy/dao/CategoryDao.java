package by.itacademy.dao;

import by.itacademy.entity.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDao implements BaseDao<Long, Category> {

    private static final CategoryDao INSTANCE = new CategoryDao();

    public static CategoryDao getInstance() {
        return INSTANCE;
    }
}
