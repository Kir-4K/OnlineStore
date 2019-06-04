package by.itacademy.servlet;

import by.itacademy.dto.FilterDto;
import by.itacademy.dto.LimitOffsetDto;
import by.itacademy.entity.Product;
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
import static by.itacademy.servlet.util.StringFormatter.ratingToDouble;
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

        String category = ofNullable(req.getParameter("category")).orElse(EMPTY);
        String rating = ofNullable(req.getParameter("rating")).orElse(EMPTY);

        if (!(category.equals(EMPTY) && rating.equals(EMPTY))) {
            LimitOffsetDto limitOffsetDto = getLimitOffsetDto(limit, offset);
            BooleanExpression expression = product.price.goe(minToDouble(minPrice))
                    .and(product.price.loe(maxToDouble(maxPrice)))
                    .and(product.category.name.eq(category))
                    .and(product.rating.goe(ratingToDouble(rating)));
            OrderSpecifier<Double> specifier = product.rating.desc();
            FilterDto filterDto = FilterDto.builder()
                    .predicates(expression)
                    .limitOffset(limitOffsetDto)
                    .specifiers(specifier)
                    .build();
            getProductList(req, productService.findAll(filterDto));
        } else {
            getProductList(req, productService.findAll());
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
