package by.itacademy.kostusev.util;

import by.itacademy.kostusev.entity.ProductOrder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ofPattern;

@Component
public class OrderTimeFormatter {

    private static final String DATE_TIME_PATTERN = "dd/MM/yyyy HH:mm:ss";

    public String getFormattedTime(ProductOrder order) {
        DateTimeFormatter pattern = DateTimeFormatter.ofPattern(DATE_TIME_PATTERN);
        String text = order.getId().getOrder().getDate().format(pattern);
        LocalDateTime time = LocalDateTime.parse(text, pattern);
        return ofPattern(DATE_TIME_PATTERN).format(time);
    }
}
