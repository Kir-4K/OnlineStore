package by.itacademy.kostusev.util;

import com.querydsl.core.types.OrderSpecifier;
import lombok.experimental.UtilityClass;

import java.util.Map;

import static by.itacademy.kostusev.entity.QProduct.product;

@UtilityClass
public class SortingUtil {

    public static OrderSpecifier<?> orderBy(String orderBy) {
        Map<String, OrderSpecifier<?>> map = Map.of(
                "Сортировать по...", product.id.asc(),
                "rating_desc", product.rating.desc(),
                "price_desc", product.price.desc(),
                "price_asc", product.price.asc(),
                "name_desc", product.name.desc(),
                "name_asc", product.name.asc()
        );
        return map.get(orderBy);
    }
}
