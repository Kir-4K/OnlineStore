package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.ProductOrderPK;
import by.itacademy.kostusev.entity.ProductOrder;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends PagingAndSortingRepository<ProductOrder, ProductOrderPK> {

    @Modifying
    @Query("UPDATE ProductOrder po " +
            "SET po.quantity =:quantity " +
            "WHERE po.id = :id")
    int update(@Param("id") ProductOrderPK id,
               @Param("quantity") Integer quantity);
}
