package ir.maktab.service.base.impl;

import ir.maktab.model.base.BaseEntity;
import ir.maktab.service.base.BaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.JpaRepository;

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
    public E update(E inpEn) {
        Optional<E> existing = repository.findById((PK) inpEn.getId());
        copyNonNullProperties(inpEn, existing.get());
        return repository.save(existing.get());
    }

    private void copyNonNullProperties(Object src, Object target) {
        BeanUtils.copyProperties(src, target, getNullPropertyNames(src));
    }

    private String[] getNullPropertyNames (Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
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
