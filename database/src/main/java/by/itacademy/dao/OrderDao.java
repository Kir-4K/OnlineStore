package by.itacademy.dao;

import by.itacademy.entity.Order;
import by.itacademy.entity.QOrder;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static by.itacademy.entity.QOrder.order;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDao implements BaseDao<Long, Order>, BaseFilter<Long, Order, QOrder> {

    private static final OrderDao INSTANCE = new OrderDao();

    public static OrderDao getInstance() {
        return INSTANCE;
    }

    @Override
    public QOrder getQEntity() {
        return order;
    }
}
