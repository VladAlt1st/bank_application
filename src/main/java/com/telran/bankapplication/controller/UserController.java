package com.telran.bankapplication.controller;

import com.telran.bankapplication.dto.UserDto;
import com.telran.bankapplication.entity.User;
import com.telran.bankapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/update")
    public User updateUser(@RequestBody UserDto userDto) {
        return userService.updateUser(userDto);
    }
}
