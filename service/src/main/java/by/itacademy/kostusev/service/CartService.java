package by.itacademy.kostusev.service;

import by.itacademy.kostusev.dto.ProductDto;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

import java.util.HashMap;
import java.util.Map;

import static java.lang.Double.parseDouble;

@Service
@SessionScope
public class CartService {

    private static final Integer DEFAULT_VALUE = 1;
    private Map<ProductDto, Integer> cart = new HashMap<>();

    public Map<ProductDto, Integer> getCart() {
        return cart;
    }

    public void addToCart(ProductDto productDto) {
        cart.merge(productDto, DEFAULT_VALUE, Integer::sum);
    }

    public double getTotalSum(Map<ProductDto, Integer> cart) {
        final Double[] sum = {0D};
        cart.forEach((product, value) -> sum[0] += parseDouble(product.getPrice()) * value);
        return sum[0];
    }

    public void deleteFromCart(Map<ProductDto, Integer> cart, ProductDto product) {
        cart.merge(product, DEFAULT_VALUE, (a, b) -> a - b);
        cart.entrySet().removeIf(entry -> entry.getValue().equals(0));
    }

    public void clearCart(Map<ProductDto, Integer> cart) {
        cart.clear();
    }
}
