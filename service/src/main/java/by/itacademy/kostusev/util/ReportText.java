package by.itacademy.kostusev.util;

import by.itacademy.kostusev.entity.ProductOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReportText {

    private final OrderTimeFormatter timeFormatter;

    public String getReportText(ProductOrder order) {
        return format("Спасибо за Ваш заказ!\n" +
                        "Номер заказа: №%s \n" +
                        "Дата заказа: %s \n\n" +
                        "Товары в заказе: \n",
                order.getId().getOrder().getId(),
                timeFormatter.getFormattedTime(order));
    }

    public String getProductInReportText(List<ProductOrder> products) {
        StringBuilder builder = new StringBuilder();
        for (ProductOrder product : products) {
            builder.append(format("%s в количестве %d\n",
                    product.getId().getProduct().getName(),
                    product.getQuantity()));
        }
        return builder.toString();
    }
}
