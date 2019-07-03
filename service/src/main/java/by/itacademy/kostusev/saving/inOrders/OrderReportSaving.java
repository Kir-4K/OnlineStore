package by.itacademy.kostusev.saving.inOrders;

import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.service.ProductOrderService;
import by.itacademy.kostusev.util.ReportText;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OrderReportSaving {

    private static final String PATH_PREFIX = "../../JD2-OnlineStore/web/src/main/resources/orders_report/order_";
    private static final String PATH_SUFFIX = ".txt";

    private final ProductOrderService productOrderService;
    private final ReportText reportText;

    public void saveReport(ProductOrder order) throws IOException {
        List<ProductOrder> products = productOrderService.getCurrentProductsFromOrder(order);
        String filename = PATH_PREFIX + order.getId().getOrder().getId() + PATH_SUFFIX;

        @Cleanup BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filename));
        bufferedWriter.write(reportText.getReportText(order));
        bufferedWriter.write(reportText.getProductInReportText(products));
    }
}
