package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.OnlineOrder;
import by.itacademy.kostusev.entity.Payment;
import by.itacademy.kostusev.entity.Status;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OnlineOrderRepository extends PagingAndSortingRepository<OnlineOrder, Long>, QuerydslPredicateExecutor<OnlineOrder> {

    @Modifying
    @Query("UPDATE OnlineOrder o " +
            "SET o.payment =:payment," +
            "o.status =:status, " +
            "o.date =:date " +
            "WHERE o.id = :id")
    int update(@Param("id") Long id,
               @Param("payment") Payment payment,
               @Param("status") Status status,
               @Param("date") LocalDateTime date);
}
