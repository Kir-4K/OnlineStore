package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.Address;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends PagingAndSortingRepository<Address, Long> {

    @Modifying
    @Query("UPDATE Address a " +
            "SET a.city =:city, " +
            "a.street =:street, " +
            "a.house =:house, " +
            "a.apartment =:apartment " +
            "WHERE a.id = :id")
    int update(@Param("id") Long id,
               @Param("city") String city,
               @Param("street") String street,
               @Param("house") String house,
               @Param("apartment") String apartment);
}
