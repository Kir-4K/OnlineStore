package by.itacademy.kostusev.service.saving.inOrders;

import by.itacademy.kostusev.dto.AddressDto;
import by.itacademy.kostusev.dto.utilityDto.CheckoutDto;
import by.itacademy.kostusev.entity.Address;
import by.itacademy.kostusev.mapper.AddressMapper;
import by.itacademy.kostusev.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isAllBlank;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AddressSaving {

    private final AddressService addressService;
    private final AddressMapper addressMapper;

    public Address getAddress(CheckoutDto checkout) {
        AddressDto address = addressService.findByCustomersPhone(checkout.getPhone());
        return isEmpty(address)
                ? saveIfExist(checkout)
                : updateIfExist(address, checkout);
    }

    private Address saveIfExist(CheckoutDto checkout) {
        return isAllBlank(checkout.getCity(), checkout.getStreet(), checkout.getHouse(), checkout.getApartment())
                ? null
                : saveAddress(checkout);
    }

    private Address saveAddress(CheckoutDto checkout) {
        AddressDto newAddress = AddressDto.builder().build();
        return updateAddress(newAddress, checkout);
    }

    private Address updateIfExist(AddressDto address, CheckoutDto checkout) {
        return isAllBlank(checkout.getCity(), checkout.getStreet(), checkout.getHouse(), checkout.getApartment())
                ? addressMapper.toEntity(address)
                : updateAddress(address, checkout);
    }

    private Address updateAddress(AddressDto address, CheckoutDto checkout) {
        address.setCity(checkout.getCity());
        address.setStreet(checkout.getStreet());
        address.setHouse(checkout.getHouse());
        address.setApartment(checkout.getApartment());
        return addressService.saveOrUpdateAddress(address);
    }
}
