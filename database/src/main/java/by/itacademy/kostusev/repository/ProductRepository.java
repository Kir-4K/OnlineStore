package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.Product;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, QuerydslPredicateExecutor<Product> {
}
