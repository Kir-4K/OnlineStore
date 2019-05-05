package by.itacademy.mapper;

import by.itacademy.entity.BaseEntity;

public interface BaseMapper<E extends BaseEntity, D> {

    D mapToDto(E entity);

    E mapToEntity(D dto);
}
