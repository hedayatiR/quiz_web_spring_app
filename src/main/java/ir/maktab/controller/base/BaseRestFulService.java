package ir.maktab.controller.base;

import ir.maktab.mapper.base.BaseMapper;
import ir.maktab.model.base.BaseDTO;
import ir.maktab.model.base.BaseEntity;
import ir.maktab.service.base.BaseService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

@Setter
@Getter

public abstract class BaseRestFulService< E extends BaseEntity<PK>, D extends BaseDTO<PK>, PK extends Serializable,
        Service extends BaseService<E, PK>, Mapper extends BaseMapper<E,D> > {

    protected Service service;
    protected Mapper baseMapper;

    private  Class<D>  dtoClass;
    private  Class<E>  entityClass;


    public BaseRestFulService(Service service, Mapper baseMapper) {
        this.service = service;
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


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Set<D>> getAll() {
        Set<E> entityList = service.findAll();
        return ResponseEntity.ok(baseMapper.entityToDtoSet(entityList, dtoClass));
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/{id}")
    public ResponseEntity<D> getById(@PathVariable PK id) {
        E e = service.findOne(id);

        if (e != null)
            return ResponseEntity.ok(baseMapper.entityToDto(e, dtoClass));
        return ResponseEntity.notFound().build();
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> create(@RequestBody D d) {
        if (d.getId() != null) {
            return ResponseEntity
                    .badRequest()
                    .header("id exists", "A new entity cannot already have an ID")
                    .build();
        }

        E e = service.save(baseMapper.dtoToEntity(d, entityClass));
        return ResponseEntity.ok(baseMapper.entityToDto(e, dtoClass));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> update(@RequestBody D d) {
        if (d.getId() == null) {
            return ResponseEntity
                    .badRequest()
                    .header("id is null", "entity must have an ID to update")
                    .build();
        }
        E e = service.save(baseMapper.dtoToEntity(d, entityClass));
        return ResponseEntity.ok(baseMapper.entityToDto(e, dtoClass));
    }


    @DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable PK id) {
        service.delete(id);
        return ResponseEntity.ok().header("deleted", "successful").build();
    }

}
