package ir.maktab.mapper.base;

import java.util.Collection;

public interface BaseMapper<E, D> {
    E dtoToEntity(D d, Class<E> eClass);

    D entityToDto(E e, Class<D> dClass);

    Collection<E> dtoToEntityCollection(Collection<D> collection, Class<E> eClass);

    Collection<D> entityToDtoCollection(Collection<E> collection, Class<D> dClass);
}
