package by.itacademy.kostusev.saving.inOrders;

import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.Product;
import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.entity.ProductOrderPK;
import by.itacademy.kostusev.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import static java.lang.Double.parseDouble;
import static java.lang.Long.parseLong;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductInOrderSaving {

    private final ProductOrderService productOrderService;

    public ProductOrder saveProductInOrder(Map<ProductDto, Integer> cart, OnlineOrder order) {
        AtomicReference<ProductOrder> productOrder = new AtomicReference<>(new ProductOrder());
        cart.forEach((product, quantity) -> productOrder.set(productOrderService.save(ProductOrder.builder()
                .quantity(quantity)
                .id(ProductOrderPK.builder()
                        .product(Product.builder()
                                .id(parseLong(product.getId()))
                                .name(product.getName())
                                .price(parseDouble(product.getPrice()))
                                .build())
                        .order(order)
                        .build())
                .build())));

        return productOrder.get();
    }
}
