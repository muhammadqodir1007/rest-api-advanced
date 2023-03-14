package com.epam.esm.service;

import com.epam.esm.dao.BasicDao;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NoSuchEntityException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

@Service
public abstract class AbstractService<T> implements CRUDService<T> {
    protected final BasicDao<T> dao;

    public AbstractService(BasicDao<T> dao) {
        this.dao = dao;
    }

    public T getById(long id) {
        Optional<T> optionalEntity = dao.findById(id);
        return optionalEntity.orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY));
    }

    public List<T> getAll(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        return dao.findAll(pageRequest);
    }

    public void removeById(long id) {

        dao.findById(id).orElseThrow(() -> {

            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        });

        dao.removeById(id);
    }

    protected String getSingleRequestParameter(MultiValueMap<String, String> requestParams, String parameter) {
        return requestParams.getFirst(parameter);
    }

    protected Pageable createPageRequest(int page, int size) {
        try {
            return PageRequest.of(page, size);
        } catch (IllegalArgumentException e) {
            ExceptionResult exceptionResult = new ExceptionResult();
            exceptionResult.addException(ExceptionMessageKey.INVALID_PAGINATION, page, size);
            throw new IncorrectParameterException(exceptionResult);
        }
    }
}
