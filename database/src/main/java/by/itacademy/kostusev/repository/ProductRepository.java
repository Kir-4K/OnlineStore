package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.Product;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, Long>, QuerydslPredicateExecutor<Product> {

    @Modifying
    @Query("UPDATE Product p " +
            "SET p.name =:name, " +
            "p.price =:price, " +
            "p.number =:number, " +
            "p.rating =:rating, " +
            "p.description =:description " +
            "WHERE p.id = :id")
    int update(@Param("id") Long id,
               @Param("name") String name,
               @Param("price") Double price,
               @Param("number") Integer number,
               @Param("rating") Double rating,
               @Param("description") String description);
}
