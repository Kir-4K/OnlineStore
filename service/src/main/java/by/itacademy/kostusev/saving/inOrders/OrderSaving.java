package by.itacademy.kostusev.saving.inOrders;

import by.itacademy.kostusev.dto.OnlineOrderDto;
import by.itacademy.kostusev.dto.utilityDto.CheckoutDto;
import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.Payment;
import by.itacademy.kostusev.entity.Status;
import by.itacademy.kostusev.service.OnlineOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderSaving {

    private final OnlineOrderService onlineOrderService;

    public OnlineOrder saveOrderAndGet(CheckoutDto checkout, Customer customer) {
        OnlineOrderDto newOrder = OnlineOrderDto.builder().build();
        newOrder.setStatus(Status.UNPROCESSED);
        newOrder.setDate(LocalDateTime.now());
        newOrder.setPayment(Payment.valueOf(checkout.getPayment()));
        newOrder.setCustomer(customer);
        return onlineOrderService.saveOrUpdateOrder(newOrder);
    }
}
