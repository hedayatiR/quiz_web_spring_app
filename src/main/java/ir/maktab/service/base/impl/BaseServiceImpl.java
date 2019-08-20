package ir.maktab.service.base.impl;

import ir.maktab.mapper.base.BaseMapper;
import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.base.BaseEntity;
import ir.maktab.service.base.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

public abstract class BaseServiceImpl<E extends BaseEntity, D extends BaseDTO<PK>, PK extends Serializable,
        Repository extends JpaRepository<E, PK>, Mapper extends BaseMapper<E,D>>
        implements BaseService<E, D, PK, Mapper> {

    protected final Repository repository;
    protected Mapper baseMapper;

    protected Class<D>  dtoClass;
    protected Class<E>  entityClass;


    public BaseServiceImpl(Repository repository, Mapper baseMapper) {
        this.repository = repository;
        this.baseMapper = baseMapper;

        ParameterizedType genericSuperclass =  (ParameterizedType)  getClass () .getGenericSuperclass ();
        Type typeD =  genericSuperclass.getActualTypeArguments () [ 1 ];
        if  (typeD  instanceof  ParameterizedType) {
            this .dtoClass =  (Class< D> ) ((ParameterizedType) typeD) .getRawType ();
        }  else  {
            this .dtoClass =  (Class< D> ) typeD;
        }

        Type typeE =  genericSuperclass.getActualTypeArguments () [ 0 ];
        if  (typeE  instanceof  ParameterizedType) {
            this .entityClass =  (Class< E> ) ((ParameterizedType) typeE) .getRawType ();
        }  else  {
            this .entityClass =  (Class< E> ) typeE;
        }
    }


    @Override
    public D save(D d) {
        E e = baseMapper.dtoToEntity(d, entityClass);
        E eSaved = repository.save(e);
        return baseMapper.entityToDto(eSaved, dtoClass);
    }


    @Override
    public D update(D d) {
        E e = baseMapper.dtoToEntity(d, entityClass);
        E eUpdated = repository.save(e);
        return baseMapper.entityToDto(eUpdated, dtoClass);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<D> findAll() {
        List<E> eList = repository.findAll();
        return baseMapper.entityToDtoCollection(eList, dtoClass);
    }

    @Override
    @Transactional(readOnly = true)
    public D findOne(PK id) {
        E e = repository.getOne(id);
        return baseMapper.entityToDto(e, dtoClass);
    }

    @Override
    public void delete(PK id) {
        repository.deleteById(id);
    }
//
    @Override
    @Transactional(readOnly = true)
    public Long countAll() {
        return repository.count();
    }
}
