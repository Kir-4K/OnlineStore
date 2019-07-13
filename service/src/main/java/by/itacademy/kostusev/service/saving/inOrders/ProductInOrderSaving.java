package by.itacademy.kostusev.service.saving.inOrders;

import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.entity.ProductOrderPK;
import by.itacademy.kostusev.mapper.ProductMapper;
import by.itacademy.kostusev.service.ProductOrderService;
import by.itacademy.kostusev.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductInOrderSaving {

    private final ProductOrderService productOrderService;
    private final ProductService productService;
    private final ProductMapper productMapper;

    public ProductOrder saveProductInOrder(Map<ProductDto, Integer> cart, OnlineOrder order) {
        AtomicReference<ProductOrder> productOrder = new AtomicReference<>(new ProductOrder());

        cart.forEach((product, quantity) -> {
            ProductDto productDto = productService.findById(product.getId());
            productOrder.set(productOrderService.save(ProductOrder.builder()
                    .quantity(quantity)
                    .id(ProductOrderPK.builder()
                            .product(productMapper.toEntity(productDto))
                            .order(order)
                            .build())
                    .build()));
        });

        return productOrder.get();
    }
}
