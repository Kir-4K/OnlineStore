package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.Address;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Long>, QuerydslPredicateExecutor<Address> {

    @Query("SELECT a FROM Address a JOIN a.customers c WHERE c.phone =:phone")
    Optional<Address> findByCustomersPhone(@Param("phone") String phone);
}
