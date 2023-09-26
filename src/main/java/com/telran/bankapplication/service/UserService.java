package com.telran.bankapplication.service;

import com.telran.bankapplication.dto.UserDto;
import com.telran.bankapplication.entity.User;

public interface UserService {

    User updateUser(UserDto userDto);

    void deleteUserById(Long userId);

}
