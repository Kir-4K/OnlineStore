package by.itacademy.kostusev.mapper;

import by.itacademy.kostusev.dto.OnlineOrderDto;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OnlineOrderMapper {

    private final CustomerMapper customerMapper;

    public OnlineOrderDto toDto(OnlineOrder entity) {
        return OnlineOrderDto.builder()
                .id(entity.getId())
                .payment(entity.getPayment())
                .date(entity.getDate())
                .status(entity.getStatus())
                .customer(customerMapper.toDto(entity.getCustomer()))
                .build();
    }

    public OnlineOrder toEntity(OnlineOrderDto dto) {
        return OnlineOrder.builder()
                .id(dto.getId())
                .payment(dto.getPayment())
                .date(dto.getDate())
                .status(Status.UNPROCESSED)
                .customer(customerMapper.toEntity(dto.getCustomer()))
                .build();
    }
}
