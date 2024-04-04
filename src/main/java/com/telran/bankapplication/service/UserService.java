package com.telran.bankapplication.service;

import com.telran.bankapplication.dto.UserDto;
import com.telran.bankapplication.entity.enums.UserStatus;

import java.util.List;

public interface UserService {

    Long createUser(UserDto userDto);

    List<UserDto> getClientsWhereStatusIs(UserStatus userStatus);
}
