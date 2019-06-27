package by.itacademy.kostusev.saving;

import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.dto.utilityDto.CheckoutDto;
import by.itacademy.kostusev.entity.Address;
import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.mapper.CustomerMapper;
import by.itacademy.kostusev.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerSaving {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public Customer getCustomer(CheckoutDto checkout, Address address) {
        CustomerDto customer = customerService.findByPhone(checkout.getPhone());
        return isEmpty(customer)
                ? saveNewCustomer(checkout, address)
                : getAddressInDatabase(customer);
    }

    private Customer getAddressInDatabase(CustomerDto customer) {
        return customerMapper.toEntity(customer);
    }

    private Customer saveNewCustomer(CheckoutDto checkout, Address address) {
        return customerService.saveNewCustomer(CustomerDto.builder()
                .firstName(checkout.getFirstName())
                .lastName(checkout.getLastName())
                .mail(getCheckoutOrNull(checkout.getMail()))
                .phone(checkout.getPhone())
                .address(address)
                .build());
    }

    private String getCheckoutOrNull(String string) {
        return isNotEmpty(string) ? string : null;
    }
}
