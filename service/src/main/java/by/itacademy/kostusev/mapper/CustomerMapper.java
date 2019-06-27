package by.itacademy.kostusev.mapper;

import by.itacademy.kostusev.dto.CustomerDto;
import by.itacademy.kostusev.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

    public CustomerDto toDto(Customer entity) {
        return CustomerDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .mail(entity.getMail())
                .phone(entity.getPhone())
                .user(entity.getUser())
                .address(entity.getAddress())
                .build();
    }

    public Customer toEntity(CustomerDto dto) {
        return Customer.builder()
                .id(dto.getId())
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .mail(dto.getMail())
                .phone(dto.getPhone())
                .user(dto.getUser())
                .address(dto.getAddress())
                .build();
    }
}
