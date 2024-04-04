package com.telran.bankapplication.controller;

import com.telran.bankapplication.dto.UserDto;
import com.telran.bankapplication.entity.enums.UserStatus;
import com.telran.bankapplication.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/new")
    @ResponseStatus(HttpStatus.CREATED)
    public Long createUser(@RequestBody @Valid UserDto userDto) {
        return service.createUser(userDto);
    }

    @GetMapping("/client/all/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<UserDto> getAllClientsWhereStatusIs(@PathVariable("status") UserStatus userStatus) {
        return service.getClientsWhereStatusIs(userStatus);
    }
}
