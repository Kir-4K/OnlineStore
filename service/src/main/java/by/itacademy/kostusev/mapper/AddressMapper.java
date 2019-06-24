package by.itacademy.kostusev.mapper;

import by.itacademy.kostusev.dto.AddressDto;
import by.itacademy.kostusev.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {

    public AddressDto toDto(Address entity) {
        return AddressDto.builder()
                .city(entity.getCity())
                .street(entity.getStreet())
                .house(entity.getHouse())
                .apartment(entity.getApartment())
                .build();
    }

    public Address toEntity(AddressDto dto) {
        return Address.builder()
                .city(dto.getCity())
                .house(dto.getHouse())
                .street(dto.getStreet())
                .apartment(dto.getApartment())
                .build();
    }
}
