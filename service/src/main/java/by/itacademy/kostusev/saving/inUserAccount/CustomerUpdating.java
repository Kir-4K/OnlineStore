package by.itacademy.kostusev.saving.inUserAccount;

import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.stripToNull;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerUpdating {

    private final CustomerService customerService;

    public Customer saveOrUpdateCustomer(CustomerDto updateCustomer, CustomerDto sessionCustomer) {
        CustomerDto customerByPhone = customerService.findByPhone(updateCustomer.getPhone());
        return isNotEmpty(sessionCustomer) ? updateCustomer(updateCustomer, sessionCustomer)
                : isNotEmpty(customerByPhone) ? updateCustomer(updateCustomer, customerByPhone)
                : saveCustomer(updateCustomer);
    }

    private Customer saveCustomer(CustomerDto formData) {
        CustomerDto newCustomer = CustomerDto.builder().build();
        return updateCustomer(formData, newCustomer);
    }

    private Customer updateCustomer(CustomerDto updateCustomer, CustomerDto sessionCustomer) {
        sessionCustomer.setFirstName(updateCustomer.getFirstName());
        sessionCustomer.setLastName(stripToNull(updateCustomer.getLastName()));
        sessionCustomer.setMail(stripToNull(updateCustomer.getMail()));
        sessionCustomer.setPhone(updateCustomer.getPhone());
        sessionCustomer.setUser(sessionCustomer.getUser());
        return customerService.saveOrUpdateCustomer(sessionCustomer);
    }
}
