package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.Category;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Optional<Category> findByName(String name);

    @Modifying
    @Query("UPDATE Category c " +
            "SET c.name =:name " +
            "WHERE c.id = :id")
    int update(@Param("id") Long id, @Param("name") String name);
}
