package ir.maktab.mapper.base.impl;

import ir.maktab.mapper.base.BaseMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class BaseMapperImpl<E,D> implements BaseMapper<E,D> {

    protected ModelMapper modelMapper;

    @Autowired
    public BaseMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public E dtoToEntity(D d, Class<E> eClass) {
        return modelMapper.map(d,eClass) ;
    }

    @Override
    public D entityToDto(E e, Class<D> dClass) {
        return modelMapper.map(e, dClass) ;
    }

    @Override
    public Collection<E> dtoToEntityCollection(Collection<D> collection, Class<E> eClass) {

        Collection<E> entitySet = collection.stream()
                .map(dto -> dtoToEntity(dto, eClass))
                .collect(Collectors.toSet());
        return entitySet;
    }

    @Override
    public Collection<D> entityToDtoCollection(Collection<E> collection, Class<D> dClass) {
        Collection<D> dtoSet = collection.stream()
                .map(entity -> entityToDto(entity, dClass))
                .collect(Collectors.toSet());
        return dtoSet;
    }

}
