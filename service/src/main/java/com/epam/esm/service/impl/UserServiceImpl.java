package com.epam.esm.service.impl;

import com.epam.esm.dao.BasicDao;
import com.epam.esm.entity.User;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;

@Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    public UserServiceImpl(BasicDao<User> dao) {
        super(dao);
    }

    @Override
    public User update(long id, User user) {
        throw new UnsupportedOperationException("Operation not supported for User entity.");
    }

    @Override
    public User insert(User user) {
        throw new UnsupportedOperationException("Operation not supported for User entity.");
    }

    @Override
    public void removeById(long id) {
        throw new UnsupportedOperationException("Operation not supported for User entity.");
    }

    @Override
    public List<User> search(MultiValueMap<String, String> requestParams, int page, int size) {
        throw new UnsupportedOperationException("Operation not supported for User entity.");
    }
}
