package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.entity.QProductOrder;
import by.itacademy.kostusev.entity.Status;
import by.itacademy.kostusev.mapper.CustomerMapper;
import by.itacademy.kostusev.repository.ProductOrderRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static java.util.Optional.ofNullable;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;
    private final CustomerMapper customerMapper;

    public List<ProductOrder> findByCustomer(CustomerDto customer) {
        return ofNullable(customer)
                .map(customerMapper::toEntity)
                .map(productOrderRepository::findById_Order_Customer)
                .orElse(null);
    }

    public List<ProductOrder> findByStatus(Status status) {
        return ofNullable(status)
                .map(productOrderRepository::findById_Order_Status)
                .orElse(null);
    }

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
