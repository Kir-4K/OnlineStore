package by.itacademy.kostusev.util;

import by.itacademy.kostusev.dto.utilityDto.ProductListDto;
import by.itacademy.kostusev.dto.utilityDto.ProductQuantityDto;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.ProductOrder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import static java.util.Comparator.comparing;
import static java.util.Optional.ofNullable;

@Component
public class OrdersForPage {

    public Map<OnlineOrder, ProductListDto> getOrder(List<ProductOrder> orderList) {
        return ofNullable(orderList)
                .map(this::putOrderInMap)
                .orElse(null);
    }

    private Map<OnlineOrder, ProductListDto> putOrderInMap(List<ProductOrder> orders) {
        Map<OnlineOrder, ProductListDto> orderMap = new TreeMap<>(comparing(OnlineOrder::getId).reversed());

        for (ProductOrder order : orders) {
            Set<ProductOrder> productOrderSet = order.getId().getOrder().getProductOrders();
            Set<ProductQuantityDto> productList = new HashSet<>();
            double totalPrice = 0D;

            for (ProductOrder product : productOrderSet) {
                productList.add(ProductQuantityDto.builder()
                        .product(product.getId().getProduct())
                        .quantity(product.getQuantity())
                        .build());
                totalPrice += product.getId().getProduct().getPrice() * product.getQuantity();
            }

            orderMap.put(
                    order.getId().getOrder(),
                    ProductListDto.builder()
                            .product(productList)
                            .totalPrice(totalPrice)
                            .build()
            );
        }

        return orderMap;
    }
}
