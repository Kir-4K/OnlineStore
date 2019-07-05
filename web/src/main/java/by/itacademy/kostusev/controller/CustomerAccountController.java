package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.dto.AddressDto;
import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.dto.UserDto;
import by.itacademy.kostusev.dto.utilityDto.ProductListDto;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.saving.inUserAccount.CustomerAddressUpdating;
import by.itacademy.kostusev.saving.inUserAccount.CustomerUpdating;
import by.itacademy.kostusev.service.CustomerService;
import by.itacademy.kostusev.service.ProductOrderService;
import by.itacademy.kostusev.service.UserService;
import by.itacademy.kostusev.util.OrderForAccount;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import static by.itacademy.kostusev.path.UrlPath.CUSTOMER_ACCOUNT_URL;
import static by.itacademy.kostusev.path.UrlPath.REDIRECT;
import static by.itacademy.kostusev.path.ViewPath.CUSTOMER_ACCOUNT_VIEW;
import static by.itacademy.kostusev.util.AttributeName.CUSTOMER_ORDERS;
import static by.itacademy.kostusev.util.AttributeName.SESSION_CUSTOMER;
import static by.itacademy.kostusev.util.AttributeName.SESSION_USER;

@Controller
@RequestMapping(CUSTOMER_ACCOUNT_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SessionAttributes({SESSION_CUSTOMER, SESSION_USER, CUSTOMER_ORDERS})
public class CustomerAccountController {

    private final ProductOrderService productOrderService;
    private final OrderForAccount orderForAccount;
    private final UserService userService;
    private final CustomerService customerService;
    private final CustomerUpdating customerUpdating;
    private final CustomerAddressUpdating addressUpdating;

    @GetMapping
    public String getUsers(Principal principal, Model model) {
        CustomerDto customer = customerService.findByUsername(principal.getName());
        UserDto user = userService.getSessionUser(principal);
        List<ProductOrder> orders = productOrderService.findByCustomer(customer);
        Map<OnlineOrder, ProductListDto> customerOrders = orderForAccount.getOrder(orders);

        model.addAttribute(SESSION_USER, user);
        model.addAttribute(SESSION_CUSTOMER, customer);
        model.addAttribute(CUSTOMER_ORDERS, customerOrders);

        return CUSTOMER_ACCOUNT_VIEW;
    }

    @PostMapping("/update_password")
    public String updatePassword(Principal principal, String password) {
        UserDto currentUser = userService.getSessionUser(principal);
        currentUser.setPassword(password);
        userService.saveOrUpdate(currentUser);

        return REDIRECT + CUSTOMER_ACCOUNT_URL;
    }

    @PostMapping("/update_customer")
    public String updateCustomer(@SessionAttribute(SESSION_USER) UserDto sessionUser, CustomerDto updateCustomer, Principal principal) {
        CustomerDto sessionCustomer = customerService.findByUsername(sessionUser.getUsername());
        customerUpdating.saveOrUpdateCustomer(updateCustomer, sessionCustomer, principal);

        return REDIRECT + CUSTOMER_ACCOUNT_URL;
    }

    @PostMapping("/update_customer_address")
    public String updateCustomerAddress(@SessionAttribute(SESSION_CUSTOMER) CustomerDto sessionCustomer, AddressDto updateAddress) {
        addressUpdating.saveOrUpdateCustomerAddress(updateAddress, sessionCustomer);

        return REDIRECT + CUSTOMER_ACCOUNT_URL;
    }
}
