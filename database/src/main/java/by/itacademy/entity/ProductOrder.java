package by.itacademy.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_order", schema = "online_store", catalog = "online_store_repository")
public class ProductOrder implements BaseEntity<ProductOrderPK> {

    @EmbeddedId
    private ProductOrderPK id;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
