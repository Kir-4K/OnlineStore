package by.itacademy.kostusev.dto;

import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.entity.Payment;
import by.itacademy.kostusev.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OnlineOrderDto {

    private Long id;
    private Payment payment;
    private LocalDateTime date;
    private Status status;
    private Customer customer;
}
