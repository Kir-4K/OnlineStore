package by.itacademy.dao;

import by.itacademy.dto.FilterDto;
import by.itacademy.dto.LimitOffsetDto;
import by.itacademy.entity.BaseEntity;
import by.itacademy.entity.Product;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.jpa.impl.JPAQuery;
import org.hibernate.Session;

import java.io.Serializable;
import java.util.List;

import static java.util.Optional.ofNullable;

public interface BaseFilter<T extends Serializable, E extends BaseEntity<T>, Q extends EntityPathBase<E>> {

    Q getQEntity();

    default E findOne(Session session, Predicate predicates) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .where(predicates)
                .fetchOne();
    }

    default List<E> findAll(Session session, FilterDto filterDto) {
        boolean predicates = isPresentPredicates(filterDto);
        boolean specifiers = isPresentSpecifiers(filterDto);
        boolean limit = isPresentLimit(filterDto);
        boolean offset = isPresentOffset(filterDto);
        return (predicates && specifiers && limit && offset) ? allWhereLimitOffsetOrderBy(session, filterDto)
                : (predicates && limit && offset) ? allWhereLimitOffset(session, filterDto)
                : (predicates && specifiers) ? allWhereOrderBy(session, filterDto)
                : (specifiers && limit && offset) ? allLimitOffsetOrderBy(session, filterDto)
                : (limit && offset) ? allLimitOffset(session, filterDto)
                : (specifiers) ? allOrderBy(session, filterDto)
                : (predicates) ? allWhere(session, filterDto)
                : all(session);
    }

    private List<E> all(Session session) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .fetch();
    }

    private List<E> allWhere(Session session, FilterDto filterDto) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .where(filterDto.getPredicates())
                .fetch();
    }

    private List<E> allOrderBy(Session session, FilterDto filterDto) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .orderBy(filterDto.getSpecifiers())
                .fetch();
    }

    private List<E> allLimitOffset(Session session, FilterDto filterDto) {
        return new JPAQuery<Product>(session)
                .select(getQEntity())
                .from(getQEntity())
                .limit(filterDto.getLimitOffset().getLimit())
                .offset(filterDto.getLimitOffset().getOffset())
                .fetch();
    }

    private List<E> allLimitOffsetOrderBy(Session session, FilterDto filterDto) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .limit(filterDto.getLimitOffset().getLimit())
                .offset(filterDto.getLimitOffset().getOffset())
                .orderBy(filterDto.getSpecifiers())
                .fetch();
    }

    private List<E> allWhereOrderBy(Session session, FilterDto filterDto) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .where(filterDto.getPredicates())
                .orderBy(filterDto.getSpecifiers())
                .fetch();
    }

    private List<E> allWhereLimitOffset(Session session, FilterDto filterDto) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .where(filterDto.getPredicates())
                .limit(filterDto.getLimitOffset().getLimit())
                .offset(filterDto.getLimitOffset().getOffset())
                .fetch();
    }

    private List<E> allWhereLimitOffsetOrderBy(Session session, FilterDto filterDto) {
        return new JPAQuery<E>(session)
                .select(getQEntity())
                .from(getQEntity())
                .where(filterDto.getPredicates())
                .limit(filterDto.getLimitOffset().getLimit())
                .offset(filterDto.getLimitOffset().getOffset())
                .orderBy(filterDto.getSpecifiers())
                .fetch();
    }

    private boolean isPresentOffset(FilterDto filterDto) {
        return ofNullable(filterDto).map(FilterDto::getLimitOffset).map(LimitOffsetDto::getOffset).isPresent();
    }

    private boolean isPresentLimit(FilterDto filterDto) {
        return ofNullable(filterDto).map(FilterDto::getLimitOffset).map(LimitOffsetDto::getLimit).isPresent();
    }

    private boolean isPresentSpecifiers(FilterDto filterDto) {
        return ofNullable(filterDto).map(FilterDto::getSpecifiers).isPresent();
    }

    private boolean isPresentPredicates(FilterDto filterDto) {
        return ofNullable(filterDto).map(FilterDto::getPredicates).isPresent();
    }
}
