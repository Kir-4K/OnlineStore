package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.util.OrderTimeFormatter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

import static by.itacademy.kostusev.path.UrlPath.SUCCESSFUL_ORDER_PAGE;
import static by.itacademy.kostusev.path.ViewPath.SUCCESSFUL_ORDER_VIEW;
import static by.itacademy.kostusev.util.AttributeName.ORDER;
import static by.itacademy.kostusev.util.AttributeName.ORDER_TIME;
import static by.itacademy.kostusev.util.AttributeName.PRODUCTS_IN_ORDER;
import static by.itacademy.kostusev.util.AttributeName.SUM;

@Controller
@RequestMapping(SUCCESSFUL_ORDER_PAGE)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SuccessfulOrderController {

    private final OrderTimeFormatter timeFormatter;

    @GetMapping
    public String successfulOrder(@SessionAttribute(ORDER) ProductOrder order,
                                  @SessionAttribute(PRODUCTS_IN_ORDER) List<ProductOrder> products,
                                  @SessionAttribute(SUM) Double sum, Model model) {
        model.addAttribute(ORDER_TIME, timeFormatter.getFormattedTime(order));

        return SUCCESSFUL_ORDER_VIEW;
    }
}
