package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.dto.utilityDto.CheckoutDto;
import by.itacademy.kostusev.entity.Address;
import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.saving.AddressSaving;
import by.itacademy.kostusev.saving.CustomerSaving;
import by.itacademy.kostusev.saving.OrderReportSaving;
import by.itacademy.kostusev.saving.OrderSaving;
import by.itacademy.kostusev.saving.ProductInOrderSaving;
import by.itacademy.kostusev.service.CartService;
import by.itacademy.kostusev.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static by.itacademy.kostusev.path.UrlPath.CHECKOUT_URL;
import static by.itacademy.kostusev.path.UrlPath.REDIRECT;
import static by.itacademy.kostusev.path.ViewPath.CHECKOUT_VIEW;
import static by.itacademy.kostusev.path.ViewPath.SUCCESSFUL_ORDER_VIEW;
import static by.itacademy.kostusev.util.AttributeName.CART;
import static by.itacademy.kostusev.util.AttributeName.ORDER;
import static by.itacademy.kostusev.util.AttributeName.PRODUCTS_IN_ORDER;

@Controller
@RequestMapping(CHECKOUT_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Validated
@SessionAttributes({ORDER, PRODUCTS_IN_ORDER})
public class CheckoutController {

    private final CartService cartService;
    private final AddressSaving addressSaving;
    private final CustomerSaving customerSaving;
    private final OrderSaving orderSaving;
    private final ProductInOrderSaving productInOrderSaving;
    private final OrderReportSaving reportSaving;
    private final ProductOrderService productOrderService;

    @GetMapping
    public String checkout() {
        return CHECKOUT_VIEW;
    }

    @PostMapping
    public String checkout(@NotBlank @SessionAttribute(CART) Map<ProductDto, Integer> cart,
                           @Valid CheckoutDto checkout,
                           BindingResult result,
                           Model model) throws IOException {

        if (result.hasErrors()) {
            return CHECKOUT_VIEW;
        }

        ProductOrder order = makeAnOrder(checkout, cart);

        model.addAttribute(ORDER, order);
        model.addAttribute(PRODUCTS_IN_ORDER, getCurrentProductsFromOrder(order));

        reportSaving.saveReport(order);
        cartService.clearCart(cart);

        return REDIRECT + SUCCESSFUL_ORDER_VIEW;
    }

    private ProductOrder makeAnOrder(CheckoutDto checkout, Map<ProductDto, Integer> cart) {
        Address address = addressSaving.getAddress(checkout);
        Customer customer = customerSaving.getCustomer(checkout, address);
        OnlineOrder onlineOrder = orderSaving.saveNewOrderAndGet(checkout, customer);
        return productInOrderSaving.saveProductInOrder(cart, onlineOrder);
    }

    private List<ProductOrder> getCurrentProductsFromOrder(ProductOrder order) {
        return productOrderService.getCurrentProductsFromOrder(order);
    }
}
