package by.itacademy.kostusev.util;

import by.itacademy.kostusev.entity.ProductOrder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

@Component
public class OrderTimeFormatter {

    public String getFormattedTime(ProductOrder order) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String text = order.getId().getOrder().getDate().format(pattern);
        LocalDateTime time = LocalDateTime.parse(text, pattern);
        return ofPattern("dd/MM/yyyy HH:mm:ss").format(time);
    }
}
