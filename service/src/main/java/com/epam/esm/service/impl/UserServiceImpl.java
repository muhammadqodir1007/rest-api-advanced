package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.impl.UserDtoConverter;
import com.epam.esm.dto.response.ApiResponse;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.UserService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final UserDtoConverter userDtoConverter;

    public UserServiceImpl(UserDao userDao, UserDtoConverter userDtoConverter) {
        this.userDao = userDao;
        this.userDtoConverter = userDtoConverter;
    }

    @Override
    public UserDto update(long id, UserDto user) {
        throw new UnsupportedOperationException(ExceptionMessageKey.OPERATION_NOT_SUPPORTED_FOR_USER_ENTITY);
    }

    @Override
    public UserDto getById(long id) {
        User user = userDao.findById(id).orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY));
        return userDtoConverter.convertToDto(user);
    }


    @Override
    public List<UserDto> getAll(int page, int size) {
        Pageable pageRequest = createPageRequest(page, size);
        List<User> allUsers = userDao.findAll(pageRequest);
        return allUsers.stream()
                .map(userDtoConverter::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto insert(UserDto user) {
        throw new UnsupportedOperationException(ExceptionMessageKey.OPERATION_NOT_SUPPORTED_FOR_USER_ENTITY);
    }

    @Override
    public ApiResponse removeById(long id) {
        throw new UnsupportedOperationException(ExceptionMessageKey.OPERATION_NOT_SUPPORTED_FOR_USER_ENTITY);
    }

    @Override
    public List<UserDto> search(MultiValueMap<String, String> requestParams, int page, int size) {
        throw new UnsupportedOperationException(ExceptionMessageKey.OPERATION_NOT_SUPPORTED_FOR_USER_ENTITY);
    }

    protected Pageable createPageRequest(int page, int size) {
        try {
            return PageRequest.of(page, size);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(ExceptionMessageKey.INVALID_PAGINATION);
        }
    }
}
