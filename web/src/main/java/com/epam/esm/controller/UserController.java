package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.DtoConverter;
import com.epam.esm.entity.User;
import com.epam.esm.hateoas.impl.UserHateoasAdder;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final DtoConverter<User, UserDto> userDtoConverter;
    private final UserHateoasAdder hateoasAdder;

    /**
     * Get a list of all users with HATEOAS links.
     *
     * @param pageNumber the page number for pagination
     * @param pageSize   the number of users to return per page
     * @return a list of UserDto objects with HATEOAS links
     */
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "5") int pageSize
    ) {
        List<User> users = userService.getAll(pageNumber, pageSize);
        List<UserDto> userDtos = users.stream()
                .map(userDtoConverter::convertToDto)
                .collect(Collectors.toList());

        userDtos.forEach(hateoasAdder::addLinks);
        return userDtos;
    }

    /**
     * Get a user by ID with HATEOAS links.
     *
     * @param userId the ID of the user to retrieve
     * @return a UserDto object with HATEOAS links
     */
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable("userId") long userId) {
        User user = userService.getById(userId);
        UserDto userDto = userDtoConverter.convertToDto(user);
        hateoasAdder.addLinks(userDto);
        return userDto;
    }
}
