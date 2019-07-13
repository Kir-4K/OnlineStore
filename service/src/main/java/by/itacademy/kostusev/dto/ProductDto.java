package by.itacademy.kostusev.dto;

import by.itacademy.kostusev.entity.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class ProductDto {

    private Long id;
    private String name;
    private String price;
    private String number;
    private String rating;
    private String description;
    private Category category;
    private Long version;
}
