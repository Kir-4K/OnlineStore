package by.itacademy.kostusev.service;

import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.repository.ProductOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductOrderService {

    private final ProductOrderRepository productOrderRepository;

    @Transactional
    public void save(ProductOrder productOrder) {
        productOrderRepository.save(productOrder);
    }
}
