package by.itacademy.dao;

import by.itacademy.entity.ProductOrder;
import by.itacademy.entity.ProductOrderPK;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductOrderDao implements BaseDao<ProductOrderPK, ProductOrder> {

    private static final ProductOrderDao INSTANCE = new ProductOrderDao();

    public static ProductOrderDao getInstance() {
        return INSTANCE;
    }
}
