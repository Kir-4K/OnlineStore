package by.itacademy.kostusev.util;

import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.Getter;
import org.springframework.cglib.core.internal.Function;

import static org.apache.commons.lang3.ObjectUtils.isEmpty;
import static org.apache.commons.lang3.ObjectUtils.isNotEmpty;

@Getter
public class ExpressionBuilder {

    private BooleanExpression expression;

    public <V> void add(V value, Function<V, BooleanExpression> function) {
        if (isNotEmpty(value)) {
            expression = isEmpty(expression)
                    ? function.apply(value)
                    : expression.and(function.apply(value));
        }
    }
}
