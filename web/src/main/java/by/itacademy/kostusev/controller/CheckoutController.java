package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.dto.AddressDto;
import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.dto.OnlineOrderDto;
import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.dto.utilityDto.CheckoutDto;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.Payment;
import by.itacademy.kostusev.entity.Product;
import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.entity.ProductOrderPK;
import by.itacademy.kostusev.entity.Status;
import by.itacademy.kostusev.service.CartService;
import by.itacademy.kostusev.service.OnlineOrderService;
import by.itacademy.kostusev.service.ProductOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.time.LocalDateTime;
import java.util.Map;

import static by.itacademy.kostusev.path.UrlPath.CHECKOUT_URL;
import static by.itacademy.kostusev.path.UrlPath.REDIRECT;
import static by.itacademy.kostusev.path.ViewPath.CHECKOUT_VIEW;
import static by.itacademy.kostusev.path.ViewPath.PRODUCTS_VIEW;
import static by.itacademy.kostusev.util.AttributeName.CART;
import static java.lang.Long.parseLong;

@Controller
@RequestMapping(CHECKOUT_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CheckoutController {

    private final OnlineOrderService onlineOrderService;
    private final ProductOrderService productOrderService;
    private final CartService cartService;

    @GetMapping
    public String checkout() {
        return CHECKOUT_VIEW;
    }

    @PostMapping
    public String checkout(CheckoutDto checkout, @SessionAttribute(CART) Map<ProductDto, Integer> cart) {
        makeAnOrder(checkout, cart);
        cartService.clearCart(cart);
        return REDIRECT + PRODUCTS_VIEW;
    }

    private void makeAnOrder(CheckoutDto checkout, Map<ProductDto, Integer> cart) {
        LocalDateTime time = LocalDateTime.now();
        saveOrder(checkout, time);
        saveProductInOrder(cart, onlineOrderService.findByPhone(time));
    }

    private void saveOrder(CheckoutDto checkout, LocalDateTime time) {
        onlineOrderService.saveNewOrder(OnlineOrderDto.builder()
                .status(Status.UNPROCESSED)
                .date(time)
                .payment(Payment.valueOf(checkout.getPayment()))
                .customer(CustomerDto.builder()
                        .firstName(checkout.getFirstName())
                        .lastName(checkout.getLastName())
                        .mail(checkout.getMail())
                        .phone(checkout.getPhone())
                        .address(AddressDto.builder()
                                .city(checkout.getCity())
                                .street(checkout.getStreet())
                                .house(checkout.getHouse())
                                .apartment(checkout.getApartment())
                                .build())
                        .build())
                .build());
    }

    private void saveProductInOrder(Map<ProductDto, Integer> cart, OnlineOrderDto currentOrder) {
        cart.forEach((product, quantity) -> productOrderService.save(ProductOrder.builder()
                .quantity(quantity)
                .id(ProductOrderPK.builder()
                        .product(Product.builder()
                                .id(parseLong(product.getId()))
                                .build())
                        .order(OnlineOrder.builder()
                                .id(currentOrder.getId())
                                .build())
                        .build())
                .build()));
    }
}
