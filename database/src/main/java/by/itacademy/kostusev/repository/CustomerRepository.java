package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    Optional<Customer> findByPhone(String phone);

    Optional<Customer> findByUser_Username(String username);
}
