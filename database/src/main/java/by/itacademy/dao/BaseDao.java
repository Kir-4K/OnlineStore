package by.itacademy.dao;

import by.itacademy.entity.BaseEntity;
import org.hibernate.Session;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public interface BaseDao<T extends Serializable, E extends BaseEntity<T>> {

    default Optional<E> findById(Session session, T id) {
        return Optional.ofNullable(session.get(getClazz(), id));
    }

    default List<E> findAll(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<E> criteria = cb.createQuery(getClazz());
        Root<E> root = criteria.from(getClazz());
        criteria.select(root);
        return session.createQuery(criteria).list();
    }

    @SuppressWarnings("unchecked")
    default Class<E> getClazz() {
        Type entityType = ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1];
        return (Class<E>) entityType;
    }

    default void save(Session session, E entity) {
        session.beginTransaction();
        session.save(entity);
        session.getTransaction().commit();
    }

    default void update(Session session, E entity) {
        session.beginTransaction();
        session.update(entity);
        session.getTransaction().commit();
    }

    default void delete(Session session, E entity) {
        session.beginTransaction();
        session.delete(entity);
        session.getTransaction().commit();
    }
}
