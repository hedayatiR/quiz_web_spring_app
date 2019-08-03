package ir.maktab.service.base;

import java.io.Serializable;
import java.util.Set;

public interface BaseService<E, PK extends Serializable>  {

    E save(E t);

    Set<E> findAll();

    E findOne(PK id);

    void delete(PK id);

    Long countAll();

}
