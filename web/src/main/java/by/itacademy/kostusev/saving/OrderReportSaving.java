package by.itacademy.kostusev.saving;

import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.service.ProductOrderService;
import by.itacademy.kostusev.util.OrderTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderReportSaving {

    private static final String PATH_PREFIX = "../../JD2-OnlineStore/web/src/main/resources/orders_report/order_";
    private static final String PATH_SUFFIX = ".txt";

    private final ProductOrderService productOrderService;
    private final OrderTimeFormatter timeFormatter;

    public void saveReport(ProductOrder productOrder) throws IOException {
        List<ProductOrder> orders = productOrderService.getCurrentProductsFromOrder(productOrder);
        String filename = PATH_PREFIX + productOrder.getId().getOrder().getId() + PATH_SUFFIX;

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename))) {
            bufferedWriter.write(getReportText(productOrder));
            writeProduct(orders, bufferedWriter);
        }
    }

    private void writeProduct(Iterable<ProductOrder> orders, BufferedWriter writer) throws IOException {
        for (ProductOrder order : orders) {
            writer.write(format("%s в количестве %d\n",
                    order.getId().getProduct().getName(),
                    order.getQuantity()));
        }
    }

    private String getReportText(ProductOrder order) {
        return format("Спасибо, что оформили заказ в нашем магазине!\n" +
                        "Номер заказа: №%s \n" +
                        "Дата заказа: %s \n\n" +
                        "Товары в заказе: \n",
                order.getId().getOrder().getId(),
                timeFormatter.getFormattedTime(order));
    }
}
