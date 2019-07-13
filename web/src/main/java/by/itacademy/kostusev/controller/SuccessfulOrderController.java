package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.service.EmailService;
import by.itacademy.kostusev.util.OrderTimeFormatter;
import by.itacademy.kostusev.util.ResourcesLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

import static by.itacademy.kostusev.path.UrlPath.SUCCESSFUL_ORDER_URL;
import static by.itacademy.kostusev.path.ViewPath.SUCCESSFUL_ORDER_VIEW;
import static by.itacademy.kostusev.util.AttributeName.ORDER;
import static by.itacademy.kostusev.util.AttributeName.ORDER_TIME;
import static by.itacademy.kostusev.util.AttributeName.PRODUCTS_IN_ORDER;
import static by.itacademy.kostusev.util.AttributeName.SUM;
import static org.springframework.http.HttpHeaders.CONTENT_DISPOSITION;

@Controller
@RequestMapping(SUCCESSFUL_ORDER_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SessionAttributes({ORDER_TIME})
public class SuccessfulOrderController {

    private static final String PATH_PREFIX = "../../JD2-OnlineStore/web/src/main/resources/orders_report/order_";
    private static final String PATH_SUFFIX = ".txt";

    private final EmailService notificationService;
    private final ResourcesLoader loader;
    private final OrderTimeFormatter timeFormatter;

    @GetMapping
    public String successfulOrder(@SessionAttribute(ORDER) ProductOrder order,
                                  @SessionAttribute(PRODUCTS_IN_ORDER) List<ProductOrder> products,
                                  @SessionAttribute(SUM) Double sum, Model model) {
        model.addAttribute(ORDER_TIME, timeFormatter.getFormattedTime(order));

        return SUCCESSFUL_ORDER_VIEW;
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadReport(@SessionAttribute(ORDER) ProductOrder order) {
        String filename = PATH_PREFIX + order.getId().getOrder().getId() + PATH_SUFFIX;
        Resource file = loader.loadAsResource(filename);
        return ResponseEntity.ok()
                .header(CONTENT_DISPOSITION, "attachment; filename=" + file.getFilename())
                .body(file);
    }

    @PostMapping()
    public void sendReportOnMail(@SessionAttribute(ORDER) ProductOrder order,
                                 @SessionAttribute(PRODUCTS_IN_ORDER) List<ProductOrder> products,
                                 String mail) {
        notificationService.sendOrderReport(mail, order, products);
    }
}
