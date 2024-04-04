package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.dto.UserDto;
import com.telran.bankapplication.entity.User;
import com.telran.bankapplication.entity.enums.UserStatus;
import com.telran.bankapplication.mapper.UserMapper;
import com.telran.bankapplication.repository.UserRepository;
import com.telran.bankapplication.service.UserService;
import com.telran.bankapplication.service.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Long createUser(UserDto userDto) {
        User createdUser = userMapper.toEntity(userDto);
        try {
            userRepository.save(createdUser);
        } catch (DataIntegrityViolationException exception) {
            throw new UserAlreadyExistsException(String.format("User with email %s already exists.", createdUser.getEmail()));
        }
        return createdUser.getId();
    }

    @Override
    public List<UserDto> getClientsWhereStatusIs(UserStatus userStatus) {
        return userMapper.toDtos(userRepository.getAllClientsByStatus(userStatus));
    }
}
