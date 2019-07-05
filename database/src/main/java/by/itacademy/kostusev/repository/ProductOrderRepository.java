package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.Customer;
import by.itacademy.kostusev.entity.ProductOrder;
import by.itacademy.kostusev.entity.ProductOrderPK;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductOrderRepository extends PagingAndSortingRepository<ProductOrder, ProductOrderPK>, QuerydslPredicateExecutor<ProductOrder> {

    List<ProductOrder> findById_Order_Customer(Customer customer);
}
