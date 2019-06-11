package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.News;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface NewsRepository extends PagingAndSortingRepository<News, Long> {

    @Modifying
    @Query("UPDATE News n " +
            "SET n.text =:text," +
            "n.title =:title, " +
            "n.date =:date " +
            "WHERE n.id = :id")
    int update(@Param("id") Long id,
               @Param("title") String title,
               @Param("text") String text,
               @Param("date") LocalDateTime date);
}
