package com.epam.esm.dao;

import com.epam.esm.dao.creator.QueryCreator;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public abstract class AbstractDao<T> {
    @PersistenceContext
    protected EntityManager entityManager;
    protected final Class<T> entityType;

    protected abstract QueryCreator<T> getQueryCreator();

   protected AbstractDao(Class<T> entityType) {
        this.entityType = entityType;
    }

    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    public List<T> findAll(Pageable pageable) {
        return entityManager.createQuery("SELECT e FROM " + entityType.getSimpleName() + " e", entityType)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    public void removeById(long id) {
        T entity = entityManager.find(entityType, id);
        entityManager.remove(entity);
    }

    public List<T> search(MultiValueMap<String, String> fields, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> criteriaQuery = getQueryCreator().createGetQuery(fields, criteriaBuilder);

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    public T insert(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public Optional<T> findByName(String name) {
        return entityManager.createQuery("SELECT e FROM " + entityType.getSimpleName() +
                        " e WHERE e.name = :name", entityType)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst();
    }


}