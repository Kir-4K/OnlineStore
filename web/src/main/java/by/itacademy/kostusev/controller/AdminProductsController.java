package by.itacademy.kostusev.controller;

import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.service.saving.inAdmin.ProductsSaving;
import by.itacademy.kostusev.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static by.itacademy.kostusev.path.UrlPath.ADMIN_PRODUCTS_URL;
import static by.itacademy.kostusev.path.UrlPath.REDIRECT;
import static by.itacademy.kostusev.path.ViewPath.ADMIN_PRODUCTS_VIEW;
import static by.itacademy.kostusev.util.AttributeName.PRODUCTS_FOR_CHANGE;
import static by.itacademy.kostusev.util.AttributeName.PRODUCT_FOR_CHANGE;

@Controller
@RequestMapping(ADMIN_PRODUCTS_URL)
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@SessionAttributes({PRODUCTS_FOR_CHANGE, PRODUCT_FOR_CHANGE})
public class AdminProductsController {

    private final ProductService productService;
    private final ProductsSaving productsSaving;

    @GetMapping
    public String getOrders(Model model, Long productId) {
        List<ProductDto> products = productService.findAll();
        ProductDto product = productService.findById(productId);

        model.addAttribute(PRODUCTS_FOR_CHANGE, products);
        model.addAttribute(PRODUCT_FOR_CHANGE, product);

        return ADMIN_PRODUCTS_VIEW;
    }

    @PostMapping("/upload")
    public String submit(@RequestParam("file") MultipartFile file,
                         @RequestParam("category_name") String category,
                         ProductDto product) throws IOException {
        productsSaving.saveProductAndGet(file, product, category);

        return REDIRECT + ADMIN_PRODUCTS_URL;
    }
}
