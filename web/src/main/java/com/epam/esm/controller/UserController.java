package com.epam.esm.controller;

import com.epam.esm.dto.UserDto;
import com.epam.esm.hateoas.impl.UserHateoasAdder;
import com.epam.esm.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserHateoasAdder hateoasAdder;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        List<UserDto> users = userService.getAll(page, size);
        users.forEach(hateoasAdder::addLinks);
        return users;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable("id") long id) {
        UserDto user = userService.getById(id);
        hateoasAdder.addLinks(user);
        return user;
    }
}
