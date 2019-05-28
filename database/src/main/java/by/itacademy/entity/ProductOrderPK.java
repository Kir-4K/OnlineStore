package by.itacademy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class ProductOrderPK implements Serializable {

    @ManyToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Product product;
}
