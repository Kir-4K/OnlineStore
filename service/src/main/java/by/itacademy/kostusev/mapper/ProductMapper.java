package by.itacademy.kostusev.mapper;

import by.itacademy.kostusev.dto.ProductDto;
import by.itacademy.kostusev.entity.Product;
import org.springframework.stereotype.Component;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;
import static java.lang.String.valueOf;

@Component
public class ProductMapper {

    public ProductDto toDto(Product entity) {
        return ProductDto.builder()
                .id(valueOf(entity.getId()))
                .name(entity.getName())
                .description(entity.getDescription())
                .price(valueOf(entity.getPrice()))
                .rating(valueOf(entity.getRating()))
                .number(valueOf(entity.getNumber()))
                .category(entity.getCategory())
                .build();
    }

    public Product toEntity(ProductDto dto) {
        return Product.builder()
                .id(parseLong(dto.getId()))
                .name(dto.getName())
                .description(dto.getDescription())
                .price(parseDouble(dto.getPrice()))
                .rating(parseDouble(dto.getRating()))
                .number(parseInt(dto.getNumber()))
                .category(dto.getCategory())
                .build();
    }
}
