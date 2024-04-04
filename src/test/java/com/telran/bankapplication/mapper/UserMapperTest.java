package com.telran.bankapplication.mapper;

import com.telran.bankapplication.data.TestDataStorage;
import com.telran.bankapplication.dto.UserDto;
import com.telran.bankapplication.entity.User;
import com.telran.bankapplication.entity.enums.Role;
import com.telran.bankapplication.entity.enums.UserStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private static final TestDataStorage testDataStorage = new TestDataStorage();

    @Test
    void toEntity_WhenValidDto_Successful() {
        //given
        UserDto userDto = testDataStorage.getUserDto(true, true);

        //when
        User user = mapper.toEntity(userDto);

        //then
        assertNull(user.getId());
        assertEquals(Role.valueOf(userDto.getRole()), user.getRole());
        assertEquals(UserStatus.ACTIVE, user.getStatus());
        assertEquals(userDto.getFirstName(), user.getFirstName());
        assertEquals(userDto.getLastName(), user.getLastName());
        assertEquals(userDto.getEmail(), user.getEmail());
        assertEquals(userDto.getPassword(), user.getPassword());
        assertEquals(userDto.getAddress(), user.getAddress());
        assertEquals(userDto.getPhone(), user.getPhone());
        assertInstanceOf(ZonedDateTime.class, user.getCreatedAt());
        assertInstanceOf(ZonedDateTime.class, user.getUpdatedAt());
    }

    @Test
    void toEntity_WhenInvalidDto_ThrowsIllegalArgumentException() {
        //given
        UserDto invalidUserDto = testDataStorage.getUserDto(false, true);

        //when then
        assertThrows(IllegalArgumentException.class, () -> mapper.toEntity(invalidUserDto));
    }

    @Test
    void toDto_WhenValidEntity_Successful() {
        //given
        User user = testDataStorage.getUser();
        user.setId(1L);

        //when
        UserDto userDto = mapper.toDto(user);

        //then
        assertEquals(user.getId().toString(), userDto.getId());
        assertEquals(user.getRole().toString(), userDto.getRole());
        assertEquals(user.getStatus().toString(), userDto.getStatus());
        assertEquals(user.getFirstName(), userDto.getFirstName());
        assertEquals(user.getLastName(), userDto.getLastName());
        assertEquals(user.getEmail(), userDto.getEmail());
        assertNull(userDto.getPassword());
        assertEquals(user.getAddress(), userDto.getAddress());
        assertEquals(user.getPhone(), userDto.getPhone());
        assertEquals(user.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), userDto.getCreatedAt());
        assertEquals(user.getUpdatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), userDto.getUpdatedAt());
    }
}