package com.telran.bankapplication.service.impl;

import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.UserDto;
import com.telran.bankapplication.entity.User;
import com.telran.bankapplication.entity.enums.UserStatus;
import com.telran.bankapplication.mapper.UserMapper;
import com.telran.bankapplication.repository.UserRepository;
import com.telran.bankapplication.service.exception.UserAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserServiceImpl userService;
    private UserDto userDto;
    private User user;
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    private void setUpForCreateUser() {
        userDto = testDataStorage.getUserDto(true, true);
        user = testDataStorage.getUser();
        when(userMapper.toEntity(userDto)).thenReturn(user);
    }

    @Test
    void createUser_WhenSuccessful_ReturnsId() {
        //given
        setUpForCreateUser();
        when(userRepository.save(user)).then(invocation -> {
            User u = invocation.getArgument(0);
            u.setId(1L);
            return u;
        });

        //when
        Long actualId = userService.createUser(userDto);

        //then
        verify(userMapper).toEntity(userDto);
        verify(userRepository).save(user);
        assertEquals(1L, actualId);
    }

    @Test
    void createUser_WhenUserAlreadyExists_ThrowsUserAlreadyExistsException() {
        //given
        setUpForCreateUser();
        when(userRepository.save(user)).thenThrow(DataIntegrityViolationException.class);

        //when then
        assertThrows(UserAlreadyExistsException.class, () -> userService.createUser(userDto));
    }

    @Test
    void getClientsWhereStatusIs_WhenSuccessful_InvokeGetAllClientsByStatusAndToDtos() {
        //given
        UserStatus status = UserStatus.ACTIVE;
        List<User> users = List.of(testDataStorage.getUser());
        List<UserDto> expectedList = List.of(testDataStorage.getUserDto(true, false));
        when(userRepository.getAllClientsByStatus(status)).thenReturn(users);
        when(userMapper.toDtos(users)).thenReturn(expectedList);

        //when
        List<UserDto> actualList = userService.getClientsWhereStatusIs(status);

        //then
        verify(userRepository).getAllClientsByStatus(status);
        verify(userMapper).toDtos(users);
        assertEquals(expectedList, actualList);
    }
}