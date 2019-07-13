package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.dto.utilityDto.ChangeOrderStatusDto;
import by.itacademy.kostusev.dto.utilityDto.ProductListDto;
import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.entity.Status;
import by.itacademy.kostusev.service.saving.inOrders.OrderSaving;
import by.itacademy.kostusev.service.ProductOrderService;
import by.itacademy.kostusev.util.OrdersForPage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;
import java.util.Map;

import static by.itacademy.kostusev.path.UrlPath.ADMIN_ORDERS_URL;
import static by.itacademy.kostusev.path.UrlPath.REDIRECT;
import static by.itacademy.kostusev.path.ViewPath.ADMIN_ORDERS_VIEW;
import static by.itacademy.kostusev.util.AttributeName.ORDERS_BY_STATUS;

@Controller
@RequestMapping(ADMIN_ORDERS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SessionAttributes({ORDERS_BY_STATUS})
public class AdminOrdersController {

    private static final String STATUS_PARAM = "?status=";
    private final ProductOrderService productOrderService;
    private final OrdersForPage ordersForPage;
    private final OrderSaving orderSaving;

    @GetMapping
    public String getOrders(Model model, Status status) {
        List<ProductOrder> orders = productOrderService.findByStatus(status);
        Map<OnlineOrder, ProductListDto> ordersByStatus = ordersForPage.getOrder(orders);

        model.addAttribute(ORDERS_BY_STATUS, ordersByStatus);

        return ADMIN_ORDERS_VIEW;
    }

    @PostMapping
    public String changeStatus(ChangeOrderStatusDto orderStatus) {
        orderSaving.updateOrderStatus(orderStatus);
        return REDIRECT + ADMIN_ORDERS_URL + STATUS_PARAM + orderStatus.getNewStatus();
    }
}
