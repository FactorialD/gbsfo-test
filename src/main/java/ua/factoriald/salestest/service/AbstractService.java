package ua.factoriald.salestest.service;

import org.springframework.beans.factory.annotation.Autowired;
import ua.factoriald.salestest.entity.AbstractEntity;
import ua.factoriald.salestest.exception.CommonException;
import ua.factoriald.salestest.repository.CommonRepository;
import ua.factoriald.salestest.util.RepositoryError;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class AbstractService<E extends AbstractEntity, R extends CommonRepository<E>>
        implements CommonService<E> {

    protected final R repository;

    public R getRepository() {
        return repository;
    }

    @Autowired
    public AbstractService(R repository) {
        this.repository = repository;
    }

    @Override
    public Optional<E> save(E entity) {
        return Optional.of(repository.save(entity));
    }

    @Override
    public List<E> saveAll(List<E> entities) {
        return new ArrayList<>(repository.saveAll(entities));
    }

    @Override
    public Optional<E> update(E entity) {
        return Optional.of(repository.save(entity));
    }

    @Override
    public Optional<E> getById(Long id) {
        return repository.findById(id);
    }

    @Override
    public List<E> getAll() {
        return new ArrayList<>(repository.findAll());
    }

    @Override
    public Boolean deleteById(Long id) {
        E entity = getById(id).orElseThrow(() -> new CommonException(String.format(RepositoryError.ENTITY_NOT_FOUND.getDescription(), id)));
        repository.delete(entity);
        return repository.findById(id).isEmpty();
    }

    @Override
    public Boolean deleteAll() {
        repository.deleteAll();
        return new ArrayList<>(repository.findAll()).isEmpty();
    }

}
