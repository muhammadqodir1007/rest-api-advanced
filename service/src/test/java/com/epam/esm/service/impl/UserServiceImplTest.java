package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.impl.UserDtoConverter;
import com.epam.esm.entity.User;
import com.epam.esm.exception.NoSuchEntityException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserDao userDao;
    @Mock
    private UserDtoConverter userDtoConverter;
    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void shouldGetById() {
        User user = new User(1, "name");
        UserDto userDto = new UserDto(1, "name");
        when(userDao.findById(anyLong())).thenReturn(Optional.of(user));
        when(userDtoConverter.convertToDto(any())).thenReturn(userDto);
        UserDto actual = userService.getById(4);
        assertEquals(userDto, actual);
    }
    @Test
    public void getByIdShouldThrowNoSuchEntityException() {
        long id = 1L;

       when(userDao.findById(id)).thenReturn(Optional.empty());

        assertThrows(NoSuchEntityException.class, () -> userService.getById(id));
    }



    @Test
    void shouldGetAll() {
        UserDto userDto = new UserDto(1, "name");
        when(userDao.findAll(any())).thenReturn(Collections.singletonList(new User(1, "name")));
        when(userDtoConverter.convertToDto(any())).thenReturn(userDto);
        List<UserDto> actual = userService.getAll(0, 5);
        assertEquals(Collections.singletonList(userDto),actual);

    }

    @Test
    void insertShouldThrowUnsupportedException(){
        assertThrows(UnsupportedOperationException.class,()->userService.insert(new UserDto(1,"Cole")));
    }
    @Test
    void updateShouldThrowUnsupportedException(){
        assertThrows(UnsupportedOperationException.class,()->userService.update(1,new UserDto(1,"Cole")));
    }
    @Test
    void removeShouldThrowUnsupportedException(){
        assertThrows(UnsupportedOperationException.class,()->userService.removeById(1));
    }
    @Test
    public void searchShouldThrowUnsupportedException() {
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("name", "John");
        assertThrows(UnsupportedOperationException.class,()->userService.search(requestParams, 0, 10));

    }


}
