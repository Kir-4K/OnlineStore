package by.itacademy.dao;

import by.itacademy.entity.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryDao {

    private static final CategoryDao INSTANCE = new CategoryDao();

    public Category findById(Session session, Long id) {
        return session.get(Category.class, id);
    }

    public static CategoryDao getInstance() {
        return INSTANCE;
    }
}
