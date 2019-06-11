package by.itacademy.kostusev.servlet;

import by.itacademy.kostusev.config.ServiceConfig;
import by.itacademy.kostusev.dto.PageDto;
import by.itacademy.kostusev.dto.ProductFilterDto;
import by.itacademy.kostusev.entity.Category;
import by.itacademy.kostusev.service.CategoryService;
import by.itacademy.kostusev.service.ProductService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;
import static org.apache.commons.lang3.StringUtils.isNotEmpty;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    private ProductService productService =
            new AnnotationConfigApplicationContext(ServiceConfig.class).getBean(ProductService.class);

    private CategoryService categoryService =
            new AnnotationConfigApplicationContext(ServiceConfig.class).getBean(CategoryService.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryParam = req.getParameter("category");
        String minPriceParam = req.getParameter("minPrice");
        String maxPriceParam = req.getParameter("maxPrice");
        String ratingParam = req.getParameter("rating");

        Category category = categoryService.findBy(categoryParam);

        ProductFilterDto productFilter = ProductFilterDto.builder()
                .minPrice(isNotEmpty(minPriceParam) ? parseDouble(minPriceParam) : 0)
                .maxPrice(isNotEmpty(maxPriceParam) ? parseDouble(maxPriceParam) : 999)
                .rating(isNotEmpty(ratingParam) ? parseDouble(ratingParam) : 0)
                .category(category)
                .build();

        if (isNotEmpty(category)) {
            req.getSession().setAttribute("filter", productFilter);
            req.getSession().setAttribute("products", productService.findAll(productFilter));
        } else {
            req.getSession().setAttribute("products", productService.findAll());
        }

        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsp/product.jsp")
                .forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        String size = req.getParameter("size");

        PageDto pageFilter = PageDto.builder()
                .page(isNotEmpty(page) ? parseInt(page) : 0)
                .size(isNotEmpty(size) ? parseInt(size) : 4)
                .build();
        ProductFilterDto filter = (ProductFilterDto) req.getSession().getAttribute("filter");

        if (isNotEmpty(filter)) {
            req.getSession().setAttribute("filter", filter);
            req.getSession().setAttribute("products", productService.findAll(filter, pageFilter));
        } else {
            req.getSession().setAttribute("products", productService.findAll(pageFilter));
        }

        getServletContext()
                .getRequestDispatcher("/WEB-INF/jsp/product.jsp")
                .forward(req, resp);
    }
}
