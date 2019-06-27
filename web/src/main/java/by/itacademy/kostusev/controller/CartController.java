package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.Map;

import static by.itacademy.kostusev.path.UrlPath.CART_URL;
import static by.itacademy.kostusev.path.UrlPath.REDIRECT;
import static by.itacademy.kostusev.path.ViewPath.CART_VIEW;
import static by.itacademy.kostusev.util.AttributeName.CART;
import static by.itacademy.kostusev.util.AttributeName.SUM;

@Controller
@RequestMapping(CART_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SessionAttributes({CART, SUM})
public class CartController {

    private final CartService cartService;

    @GetMapping
    public String getCart(Model model) {
        Map<ProductDto, Integer> cart = cartService.getCart();
        Double sum = cartService.getTotalSum(cart);
        model.addAttribute(CART, cart);
        model.addAttribute(SUM, sum);
        return CART_VIEW;
    }

    @GetMapping("/clear")
    public String clearCart() {
        Map<ProductDto, Integer> cart = cartService.getCart();
        cartService.clearCart(cart);
        return REDIRECT + CART_URL;
    }

    @GetMapping("/delete")
    public String deleteFromCart(ProductDto product) {
        Map<ProductDto, Integer> cart = cartService.getCart();
        cartService.deleteFromCart(cart, product);
        return REDIRECT + CART_URL;
    }
}
