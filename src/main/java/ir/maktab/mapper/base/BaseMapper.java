package ir.maktab.mapper.base;

import java.util.Set;

public interface BaseMapper<E, D> {
    E dtoToEntity(D d, Class<E> eClass);

    D entityToDto(E e, Class<D> dClass);

    Set<E> dtoToEntitySet(Set<D> set, Class<E> eClass);

    Set<D> entityToDtoSet(Set<E> set, Class<D> dClass);
}
