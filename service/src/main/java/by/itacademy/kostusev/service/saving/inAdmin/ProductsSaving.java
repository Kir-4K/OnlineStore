package by.itacademy.kostusev.service.saving.inAdmin;

import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.entity.Category;
import by.itacademy.kostusev.entity.Product;
import by.itacademy.kostusev.service.CategoryService;
import by.itacademy.kostusev.service.ProductService;
import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.firstNonBlank;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ProductsSaving {

    private static final String PATH_PREFIX = "../../JD2-OnlineStore/web/src/main/resources/static/img/";
    private static final String PATH_SUFFIX = ".png";
    private static final String DEFAULT_RATING = "1.0";
    private static final String DEFAULT_NUMBER = "0";

    private final ProductService productService;
    private final CategoryService categoryService;

    public Product saveProductAndGet(MultipartFile file, ProductDto product, String category) throws IOException {
        ProductDto productFromDatabase = productService.findByName(product.getName());
        return (isEmpty(productFromDatabase))
                ? saveProduct(product, category, file)
                : updateProduct(product, category, productFromDatabase);
    }

    private Product saveProduct(ProductDto product, String category, MultipartFile file) throws IOException {
        ProductDto newProduct = ProductDto.builder().build();
        product.setNumber(DEFAULT_NUMBER);
        product.setRating(DEFAULT_RATING);
        saveImg(file, product);
        return updateProduct(product, category, newProduct);
    }

    private Product updateProduct(ProductDto updateProduct, String categoryName, ProductDto productFromDatabase) {
        Category category = categoryService.findByName(categoryName);
        productFromDatabase.setName(updateProduct.getName());
        productFromDatabase.setPrice(updateProduct.getPrice());
        productFromDatabase.setRating(firstNonBlank(updateProduct.getRating(), productFromDatabase.getRating()));
        productFromDatabase.setNumber(firstNonBlank(updateProduct.getNumber(), productFromDatabase.getNumber()));
        productFromDatabase.setDescription(updateProduct.getDescription());
        productFromDatabase.setCategory(category);
        return productService.saveOrUpdateProduct(productFromDatabase);
    }

    private void saveImg(MultipartFile file, ProductDto product) throws IOException {
        String filename = PATH_PREFIX + product.getName() + PATH_SUFFIX;
        byte[] bytes = file.getBytes();
        @Cleanup BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filename)));
        stream.write(bytes);
    }
}
