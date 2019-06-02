package by.itacademy.dao;

import by.itacademy.dto.LimitOffsetDto;
import by.itacademy.entity.BaseEntity;
import by.itacademy.entity.Product;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

public interface BaseFilter<T extends Serializable, E extends BaseEntity<T>, Q extends EntityPathBase<E>> {

    Q getQEntity();

    default E findOne(Session session, Predicate predicates) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .where(predicates)
                .fetchOne();
    }

    default List<E> findAll(Session session, Predicate predicates) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .where(predicates)
                .fetch();
    }

    default List<E> findAll(Session session, OrderSpecifier<?>... specifiers) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .orderBy(specifiers)
                .fetch();
    }

    default List<E> findAll(Session session, Predicate predicates, OrderSpecifier<?>... specifiers) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .where(predicates)
                .orderBy(specifiers)
                .fetch();
    }

    default List<E> findAll(Session session, LimitOffsetDto limitOffset) {
        return new JPAQuery<Product>(session)
                .select(getQEntity())
                .from(getQEntity())
                .limit(limitOffset.getLimit())
                .offset(limitOffset.getOffset())
                .fetch();
    }
}
