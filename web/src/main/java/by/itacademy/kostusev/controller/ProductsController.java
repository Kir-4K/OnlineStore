package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.dto.utilityDto.PageDto;
import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.dto.utilityDto.ProductFilterDto;
import by.itacademy.kostusev.service.CartService;
import by.itacademy.kostusev.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.util.List;

import static by.itacademy.kostusev.path.UrlPath.PRODUCT_URL;
import static by.itacademy.kostusev.path.UrlPath.REDIRECT;
import static by.itacademy.kostusev.path.ViewPath.PRODUCTS_VIEW;
import static by.itacademy.kostusev.util.AttributeName.FILTERS;
import static by.itacademy.kostusev.util.AttributeName.PAGE;
import static by.itacademy.kostusev.util.AttributeName.PRODUCTS;
import static java.util.Optional.ofNullable;

@Controller
@RequestMapping(value = PRODUCT_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SessionAttributes(types = {ProductFilterDto.class, PageDto.class})
public class ProductsController {

    private static final Integer DEFAULT_SIZE = 4;
    private static final Integer DEFAULT_PAGE = 0;

    private final ProductService productService;
    private final CartService cartService;

    @GetMapping
    public String getProducts(Model model, ProductFilterDto filter, PageDto page) {
        filter.setMaxPrice(filter.getMaxPrice());
        filter.setMinPrice(filter.getMinPrice());
        filter.setRating(filter.getRating());
        filter.setCategory(filter.getCategory());

        page.setPage(ofNullable(page.getPage()).filter(p -> p > DEFAULT_PAGE).orElse(DEFAULT_PAGE));
        page.setSize(ofNullable(page.getSize()).filter(s -> s > DEFAULT_SIZE).orElse(DEFAULT_SIZE));

        model.addAttribute(FILTERS, filter);
        model.addAttribute(PAGE, page);

        List<ProductDto> productFilter = productService.findAll(filter, page);
        model.addAttribute(PRODUCTS, productFilter);

        return PRODUCTS_VIEW;
    }

    @PostMapping
    public String addProductInOrder(ProductDto product) {
        cartService.addToCart(product);
        return REDIRECT + PRODUCTS_VIEW;
    }
}
