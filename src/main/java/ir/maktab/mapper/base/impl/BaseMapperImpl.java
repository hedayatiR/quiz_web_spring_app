package ir.maktab.mapper.base.impl;

import ir.maktab.mapper.base.BaseMapper;
import org.modelmapper.ModelMapper;

import java.util.Set;
import java.util.stream.Collectors;

public class BaseMapperImpl<E,D> implements BaseMapper<E,D> {

    protected ModelMapper modelMapper;

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
    public Set<E> dtoToEntitySet(Set<D> set, Class<E> eClass) {

        Set<E> entitySet = set.stream()
                .map(dto -> dtoToEntity(dto, eClass))
                .collect(Collectors.toSet());
        return entitySet;
    }

    @Override
    public Set<D> entityToDtoSet(Set<E> set, Class<D> dClass) {
        Set<D> dtoSet = set.stream()
                .map(entity -> entityToDto(entity, dClass))
                .collect(Collectors.toSet());
        return dtoSet;
    }

}
