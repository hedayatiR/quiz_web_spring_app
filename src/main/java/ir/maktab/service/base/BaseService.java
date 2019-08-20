package ir.maktab.service.base;

import ir.maktab.mapper.base.BaseMapper;
import ir.maktab.model.base.BaseDTO;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

public interface BaseService<E, D extends BaseDTO<PK>, PK extends Serializable,
        Mapper extends BaseMapper<E, D>> {

    D save(D d);

    D update(D d);


    Collection<D> findAll();

    D findOne(PK id);

    void delete(PK id);

    Long countAll();

}
