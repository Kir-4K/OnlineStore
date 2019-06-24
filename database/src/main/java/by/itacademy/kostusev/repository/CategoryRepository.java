package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    Optional<Category> findByName(String name);
}
