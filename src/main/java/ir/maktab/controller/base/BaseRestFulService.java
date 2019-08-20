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
import java.util.Collection;

@Setter
@Getter

public abstract class BaseRestFulService<E extends BaseEntity<PK>, D extends BaseDTO<PK>, PK extends Serializable,
        Service extends BaseService<E, D, PK, Mapper>, Mapper extends BaseMapper<E, D>> {

    protected Service service;


    public BaseRestFulService(Service service) {
        this.service = service;
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<Collection<D>> getAll() {
        Collection<D> dtoCollection = service.findAll();
        return ResponseEntity.ok(dtoCollection);
    }


    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/{id}")
    public ResponseEntity<D> getById(@PathVariable PK id) {
        D dto = service.findOne(id);

        if (dto != null)
            return ResponseEntity.ok(dto);
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

        D dSaved = service.save(d);

        return ResponseEntity.ok(dSaved);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<D> update(@RequestBody D d) {

        if (d.getId() == null) {
            return ResponseEntity
                    .badRequest()
                    .header("id is null", "entity must have an ID to update")
                    .build();
        }

        D updatedDto = service.update(d);
        return ResponseEntity.ok(updatedDto);
    }


    @DeleteMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE, path = "/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable PK id) {
        service.delete(id);
        return ResponseEntity.ok().header("deleted", "successful").build();
    }

}
