package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.News;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends PagingAndSortingRepository<News, Long> {
}
