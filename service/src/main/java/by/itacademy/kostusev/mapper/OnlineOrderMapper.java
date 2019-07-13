package by.itacademy.kostusev.mapper;

import by.itacademy.kostusev.dto.OnlineOrderDto;
import by.itacademy.kostusev.entity.OnlineOrder;
import org.springframework.stereotype.Component;

@Component
public class OnlineOrderMapper {

    public OnlineOrderDto toDto(OnlineOrder entity) {
        return OnlineOrderDto.builder()
                .id(entity.getId())
                .payment(entity.getPayment())
                .date(entity.getDate())
                .status(entity.getStatus())
                .customer(entity.getCustomer())
                .build();
    }

    public OnlineOrder toEntity(OnlineOrderDto dto) {
        return OnlineOrder.builder()
                .id(dto.getId())
                .payment(dto.getPayment())
                .date(dto.getDate())
                .status(dto.getStatus())
                .customer(dto.getCustomer())
                .build();
    }
}
