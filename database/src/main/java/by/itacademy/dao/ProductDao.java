package by.itacademy.dao;

import by.itacademy.entity.Product;
import by.itacademy.entity.QProduct;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import static by.itacademy.entity.QProduct.product;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductDao implements BaseDao<Long, Product>, BaseFilter<Long, Product, QProduct> {

    private static final ProductDao INSTANCE = new ProductDao();

    public static ProductDao getInstance() {
        return INSTANCE;
    }

    @Override
    public QProduct getQEntity() {
        return product;
    }
}
