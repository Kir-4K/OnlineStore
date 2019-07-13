package by.itacademy.kostusev.dto.utilityDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductListDto {

    private Set<ProductQuantityDto> product;
    private Double totalPrice;
}
