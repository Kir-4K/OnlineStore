package by.itacademy.dto;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FilterDto {

    private Predicate predicates;
    private LimitOffsetDto limitOffset;
    private OrderSpecifier<?> specifiers;
}
