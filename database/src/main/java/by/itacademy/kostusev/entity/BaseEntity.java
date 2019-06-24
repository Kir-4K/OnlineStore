package by.itacademy.kostusev.entity;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
public interface BaseEntity<T extends Serializable> {

    T getId();
}
