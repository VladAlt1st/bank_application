package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.dto.UserDto;
import com.telran.bankapplication.entity.User;
import com.telran.bankapplication.entity.enums.Role;
import com.telran.bankapplication.entity.enums.UserStatus;
import com.telran.bankapplication.repository.UserRepository;
import com.telran.bankapplication.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User updateUser(UserDto userDto) {
        User user = userRepository.findUserByTaxCode(userDto.getTaxCode()).orElseThrow(
                NoSuchElementException::new
        );

        user.setRole(Role.valueOf(userDto.getRole()));
        user.setStatus(UserStatus.valueOf(userDto.getStatus()));
        user.setTaxCode(userDto.getTaxCode());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setAddress(userDto.getAddress());
        user.setPhone(userDto.getPhone());

        return user;
    }
}
