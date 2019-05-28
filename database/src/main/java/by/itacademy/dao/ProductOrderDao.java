package by.itacademy.dao;

import by.itacademy.entity.ProductOrder;
import by.itacademy.entity.ProductOrderPK;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOrderDao {

    private static final ProductOrderDao INSTANCE = new ProductOrderDao();

    public ProductOrder findById(Session session, ProductOrderPK id) {
        return session.get(ProductOrder.class, id);
    }

    public List<ProductOrder> findAll(Session session) {
        return session.
                createQuery("SELECT po FROM ProductOrder po", ProductOrder.class)
                .list();
    }

    public static ProductOrderDao getInstance() {
        return INSTANCE;
    }
}
