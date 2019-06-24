package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.entity.ProductOrderPK;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductOrderRepository extends PagingAndSortingRepository<ProductOrder, ProductOrderPK> {
}
