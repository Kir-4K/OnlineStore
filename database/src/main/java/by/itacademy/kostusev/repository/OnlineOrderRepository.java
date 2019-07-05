package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.OnlineOrder;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnlineOrderRepository extends PagingAndSortingRepository<OnlineOrder, Long>, QuerydslPredicateExecutor<OnlineOrder> {
}
