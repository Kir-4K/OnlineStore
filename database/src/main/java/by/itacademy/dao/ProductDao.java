package by.itacademy.dao;

import by.itacademy.entity.Category;
import by.itacademy.entity.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao {

    private static final ProductDao INSTANCE = new ProductDao();

    public Product findById(Session session, Long id) {
        return session.get(Product.class, id);
    }

    public List<Product> findAll(Session session) {
        return session.
                createQuery("SELECT p FROM Product p", Product.class)
                .list();
    }

    public List<Product> findByCategory(Session session, Category category) {
        return session.
                createQuery("SELECT p FROM Product p WHERE p.category.name =:category", Product.class)
                .setParameter("category", category.getName())
                .list();
    }

    public static ProductDao getInstance() {
        return INSTANCE;
    }
}
