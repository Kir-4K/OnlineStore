package by.itacademy.dao;

import by.itacademy.entity.Order;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao {

    private static final OrderDao INSTANCE = new OrderDao();

    public Order findById(Session session, Long id) {
        return session.get(Order.class, id);
    }

    public List<Order> findAll(Session session) {
        return session.
                createQuery("SELECT o FROM Order o", Order.class)
                .list();
    }

    public static OrderDao getInstance() {
        return INSTANCE;
    }
}
