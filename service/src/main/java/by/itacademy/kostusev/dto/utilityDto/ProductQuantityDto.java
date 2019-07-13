package by.itacademy.kostusev.dto.utilityDto;

import by.itacademy.kostusev.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductQuantityDto {

    private Product product;
    private Integer quantity;
}
