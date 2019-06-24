package by.itacademy.kostusev.dto.utilityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilterDto {

    private Double minPrice;
    private Double maxPrice;
    private Double rating;
    private String category;
    private String specifier;
}
