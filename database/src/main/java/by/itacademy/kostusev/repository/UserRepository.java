package by.itacademy.kostusev.repository;

import by.itacademy.kostusev.entity.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long>, QuerydslPredicateExecutor<User> {

    @Modifying
    @Query("UPDATE User u " +
            "SET u.login =:login, " +
            "u.password =:password " +
            "WHERE u.id = :id")
    int update(@Param("id") Long id,
               @Param("login") String login,
               @Param("password") String password);
}
