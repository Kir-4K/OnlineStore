package by.itacademy.servlet;

import by.itacademy.dto.LimitOffsetDto;
import by.itacademy.entity.Product;
import by.itacademy.entity.QProduct;
import by.itacademy.service.ProductService;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static by.itacademy.entity.QProduct.product;
import static by.itacademy.servlet.util.StringFormatter.limitToLong;
import static by.itacademy.servlet.util.StringFormatter.maxToDouble;
import static by.itacademy.servlet.util.StringFormatter.minToDouble;
import static by.itacademy.servlet.util.StringFormatter.offsetToLong;
import static java.util.Optional.ofNullable;


@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private static final String EMPTY = "";
    private ProductService productService = ProductService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String minPrice = ofNullable(req.getParameter("minPrice")).orElse(EMPTY);
        String maxPrice = ofNullable(req.getParameter("maxPrice")).orElse(EMPTY);

        String limit = ofNullable(req.getParameter("limit")).orElse(EMPTY);
        String offset = ofNullable(req.getParameter("offset")).orElse(EMPTY);

        if (!(limit.equals(EMPTY) && offset.equals(EMPTY))) {
            LimitOffsetDto limitOffsetDto = getLimitOffsetDto(limit, offset);
            getProductList(req, productService.findAll(limitOffsetDto));
        } else if (!(maxPrice.equals(EMPTY) && minPrice.equals(EMPTY))) {
            BooleanExpression expression = product.price.between(minToDouble(minPrice), maxToDouble(maxPrice));
            getProductList(req, productService.findAll(expression));
        } else {
            OrderSpecifier<Double> orderByPrice = QProduct.product.price.desc();
            getProductList(req, productService.findAll(orderByPrice));
        }

        getRequest(req, resp);
    }

    private LimitOffsetDto getLimitOffsetDto(String limit, String offset) {
        return LimitOffsetDto.builder()
                .limit(limitToLong(limit))
                .offset(offsetToLong(offset))
                .build();
    }

    private void getProductList(HttpServletRequest req, List<Product> list) {
        req.getSession().setAttribute("productExpression", list);
    }

    private void getRequest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsp/product.jsp")
                .forward(req, resp);
    }
}
