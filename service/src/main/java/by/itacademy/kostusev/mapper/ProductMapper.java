package by.itacademy.kostusev.mapper;

import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.entity.Product;
import org.springframework.stereotype.Component;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

@Component
public class ProductMapper {

    public ProductDto toDto(Product entity) {
        return ProductDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .price(valueOf(entity.getPrice()))
                .rating(valueOf(entity.getRating()))
                .number(valueOf(entity.getNumber()))
                .category(entity.getCategory())
                .version(entity.getVersion())
                .build();
    }

    public Product toEntity(ProductDto dto) {
        return Product.builder()
                .id(dto.getId())
                .name(dto.getName())
                .description(dto.getDescription())
                .price(parseDouble(dto.getPrice()))
                .rating(parseDouble(dto.getRating()))
                .number(parseInt(dto.getNumber()))
                .category(dto.getCategory())
                .version(dto.getVersion())
                .build();
    }
}
