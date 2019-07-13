package by.itacademy.kostusev.service.saving.inUserAccount;

import by.itacademy.kostusev.dto.AddressDto;
import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.entity.Address;
import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.stripToNull;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerAddressUpdating {

    private final CustomerService customerService;

    public Customer saveOrUpdateCustomerAddress(AddressDto updateAddress, CustomerDto sessionCustomer) {
        return isNotEmpty(sessionCustomer.getAddress())
                ? updateCustomerAddress(sessionCustomer, updateAddress)
                : saveCustomerAddress(sessionCustomer, updateAddress);
    }

    private Customer saveCustomerAddress(CustomerDto sessionCustomer, AddressDto updateAddress) {
        sessionCustomer.setAddress(Address.builder().build());
        return updateCustomerAddress(sessionCustomer, updateAddress);
    }

    private Customer updateCustomerAddress(CustomerDto sessionCustomer, AddressDto updateAddress) {
        sessionCustomer.getAddress().setCity(stripToNull(updateAddress.getCity()));
        sessionCustomer.getAddress().setStreet(stripToNull(updateAddress.getStreet()));
        sessionCustomer.getAddress().setHouse(stripToNull(updateAddress.getHouse()));
        sessionCustomer.getAddress().setApartment(stripToNull(updateAddress.getApartment()));
        return customerService.saveOrUpdateCustomer(sessionCustomer);
    }
}
