package by.itacademy.kostusev.mapper;

import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerMapper {

    private final AddressMapper addressMapper;

    public CustomerDto toDto(Customer entity) {
        return CustomerDto.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .mail(entity.getMail())
                .phone(entity.getPhone())
                .address(addressMapper.toDto(entity.getAddress()))
                .build();
    }

    public Customer toEntity(CustomerDto dto) {
        return Customer.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .mail(dto.getMail())
                .phone(dto.getPhone())
                .address(addressMapper.toEntity(dto.getAddress()))
                .build();
    }
}
