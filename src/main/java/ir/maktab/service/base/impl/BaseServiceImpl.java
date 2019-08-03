package ir.maktab.service.base.impl;

import ir.maktab.service.base.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public abstract class BaseServiceImpl<E, PK extends Serializable, Repository extends JpaRepository<E, PK>>
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
    public Set<E> findAll() {
        return new HashSet<>(repository.findAll());
    }

    @Override
    public E findOne(PK id) {
        return repository.getOne(id);
    }

    @Override
    public void delete(PK id) {
        repository.deleteById(id);
    }

    @Override
    public Long countAll() {
        return repository.count();
    }
}
