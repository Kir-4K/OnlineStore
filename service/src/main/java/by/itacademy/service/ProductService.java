package by.itacademy.service;

import by.itacademy.dao.ProductDao;
import by.itacademy.dto.LimitOffsetDto;
import by.itacademy.entity.Product;
import by.itacademy.util.ConnectionManager;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductService {

    private static final ProductService INSTANCE = new ProductService();
    private ProductDao productDao = ProductDao.getInstance();
    private Session session = ConnectionManager.getSession();

    public Product findById(Long id) {
        return productDao.findById(session, id).orElse(null);
    }

    public List<Product> findAll() {
        return productDao.findAll(session);
    }

    public List<Product> findAll(OrderSpecifier<?> specifier) {
        return productDao.findAll(session, specifier);
    }

    public List<Product> findAll(Predicate predicates) {
        return productDao.findAll(session, predicates);
    }

    public List<Product> findAll(LimitOffsetDto limitOffsetDto) {
        return productDao.findAll(session, limitOffsetDto);
    }

    public static ProductService getInstance() {
        return INSTANCE;
    }
}
