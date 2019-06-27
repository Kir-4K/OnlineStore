package by.itacademy.kostusev.saving;

import by.itacademy.kostusev.dto.AddressDto;
import by.itacademy.kostusev.dto.utilityDto.CheckoutDto;
import by.itacademy.kostusev.entity.Address;
import by.itacademy.kostusev.mapper.AddressMapper;
import by.itacademy.kostusev.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddressSaving {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public Address getAddress(CheckoutDto checkout) {
        AddressDto address = addressService.findByCustomersPhone(checkout.getPhone());
        return isEmpty(address)
                ? saveNewAddress(checkout)
                : getAddressInDatabase(address);
    }

    private Address getAddressInDatabase(AddressDto address) {
        return addressMapper.toEntity(address);
    }

    private Address saveNewAddress(CheckoutDto checkout) {
        return (isNotEmpty(checkout.getCity())
                || isNotEmpty(checkout.getStreet())
                || isNotEmpty(checkout.getHouse())
                || isNotEmpty(checkout.getApartment()))
                ? createNewAddress(checkout)
                : null;
    }

    private Address createNewAddress(CheckoutDto checkout) {
        return addressService.saveNewAddress(AddressDto.builder()
                .city(checkout.getCity())
                .street(checkout.getStreet())
                .house(checkout.getHouse())
                .apartment(checkout.getApartment())
                .build());
    }
}
