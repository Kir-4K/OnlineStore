package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.Customer;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {

    @Modifying
    @Query("UPDATE Customer c " +
            "SET c.firstName =:firstName," +
            "c.lastName =:lastName, " +
            "c.middleName =:middleName, " +
            "c.mail =:mail, " +
            "c.phone =:phone " +
            "WHERE c.id = :id")
    int update(@Param("id") Long id,
               @Param("firstName") String firstName,
               @Param("lastName") String lastName,
               @Param("middleName") String middleName,
               @Param("mail") String mail,
               @Param("phone") String phone);
}
