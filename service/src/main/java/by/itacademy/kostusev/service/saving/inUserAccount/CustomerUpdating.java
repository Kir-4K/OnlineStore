package by.itacademy.kostusev.service.saving.inUserAccount;

import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.entity.User;
import by.itacademy.kostusev.mapper.UserMapper;
import by.itacademy.kostusev.service.CustomerService;
import by.itacademy.kostusev.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.Principal;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.stripToNull;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerUpdating {

    private final CustomerService customerService;
    private final UserService userService;
    private final UserMapper userMapper;

    public Customer saveOrUpdateCustomer(CustomerDto updateCustomer, CustomerDto sessionCustomer, Principal principal) {
        CustomerDto customerByPhone = customerService.findByPhone(updateCustomer.getPhone());
        User user = userMapper.toEntity(userService.findByUsername(principal.getName()));
        return isNotEmpty(sessionCustomer) ? updateCustomer(updateCustomer, sessionCustomer, user)
                : isNotEmpty(customerByPhone) ? updateCustomer(updateCustomer, customerByPhone, user)
                : saveCustomer(updateCustomer, user);
    }

    private Customer saveCustomer(CustomerDto formData, User user) {
        CustomerDto newCustomer = CustomerDto.builder().build();
        return updateCustomer(formData, newCustomer, user);
    }

    private Customer updateCustomer(CustomerDto updateCustomer, CustomerDto sessionCustomer, User user) {
        sessionCustomer.setFirstName(updateCustomer.getFirstName());
        sessionCustomer.setLastName(stripToNull(updateCustomer.getLastName()));
        sessionCustomer.setMail(stripToNull(updateCustomer.getMail()));
        sessionCustomer.setPhone(updateCustomer.getPhone());
        sessionCustomer.setUser(user);
        return customerService.saveOrUpdateCustomer(sessionCustomer);
    }
}
