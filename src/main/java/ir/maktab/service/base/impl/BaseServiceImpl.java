package ir.maktab.service.base.impl;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.service.base.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public abstract class BaseServiceImpl<E extends BaseEntity, PK extends Serializable, Repository extends JpaRepository<E, PK>>
        implements BaseService<E, PK> {

    protected final Repository repository;

    public BaseServiceImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public E save(E t) {
        return repository.save(t);
    }

    @Override
    public E update(E e) {
        return save(e);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<E> findAll() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public E findOne(PK id) {
        return repository.getOne(id);
    }

    @Override
    public void delete(PK id) {
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countAll() {
        return repository.count();
    }
}
