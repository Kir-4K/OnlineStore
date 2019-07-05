package by.itacademy.kostusev.saving.inOrders;

import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.dto.utilityDto.CheckoutDto;
import by.itacademy.kostusev.entity.Address;
import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.entity.User;
import by.itacademy.kostusev.mapper.CustomerMapper;
import by.itacademy.kostusev.mapper.UserMapper;
import by.itacademy.kostusev.service.CustomerService;
import by.itacademy.kostusev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;

import static org.apache.commons.lang3.ObjectUtils.firstNonNull;
import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.stripToNull;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerSaving {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;
    private final UserService userService;
    private final UserMapper userMapper;

    public Customer getCustomer(CheckoutDto checkout, Address address, Principal principal) {
        CustomerDto customerByPhone = customerService.findByPhone(checkout.getPhone());
        CustomerDto customerFromSession = customerService.getSessionCustomer(principal);
        return (isEmpty(customerByPhone) && isEmpty(customerFromSession)) ? saveCustomer(checkout, address, principal)
                : isEmpty(principal) ? customerMapper.toEntity(firstNonNull(customerByPhone, customerFromSession))
                : updateCustomer(checkout, address, principal, firstNonNull(customerByPhone, customerFromSession));
    }

    private Customer saveCustomer(CheckoutDto checkout, Address address, Principal principal) {
        CustomerDto newCustomer = CustomerDto.builder().build();
        return updateCustomer(checkout, address, principal, newCustomer);
    }

    private Customer updateCustomer(CheckoutDto checkout, Address address, Principal principal, CustomerDto customer) {
        customer.setFirstName(checkout.getFirstName());
        customer.setLastName(stripToNull(checkout.getLastName()));
        customer.setMail(stripToNull(checkout.getMail()));
        customer.setPhone(checkout.getPhone());
        customer.setUser(getUser(principal));
        customer.setAddress(address);
        return customerService.saveOrUpdateCustomer(customer);
    }

    private User getUser(Principal principal) {
        return isNotEmpty(principal) ? userMapper.toEntity(userService.getSessionUser(principal)) : null;
    }
}
