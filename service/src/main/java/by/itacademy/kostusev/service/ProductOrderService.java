package by.itacademy.kostusev.service;

import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.entity.QProductOrder;
import by.itacademy.kostusev.repository.ProductOrderRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;

    public List<ProductOrder> getCurrentProductsFromOrder(ProductOrder productOrder) {
        Long orderId = productOrder.getId().getOrder().getId();
        BooleanExpression expression = QProductOrder.productOrder.id.order.id.eq(orderId);
        return newArrayList(productOrderRepository.findAll(expression));
    }

    @Transactional
    public ProductOrder save(ProductOrder productOrder) {
        return productOrderRepository.save(productOrder);
    }
}
