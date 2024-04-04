package com.telran.bankapplication.mapper;

import com.telran.bankapplication.dto.UserDto;
import com.telran.bankapplication.entity.User;
import org.mapstruct.*;

import java.time.ZonedDateTime;
import java.util.List;

import static org.mapstruct.InjectionStrategy.CONSTRUCTOR;

@Mapper(componentModel = "spring", injectionStrategy = CONSTRUCTOR,
        imports = {ZonedDateTime.class})
public interface UserMapper {

    @Mapping(target = "status", constant = "ACTIVE")
    @Mapping(target = "createdAt", expression = "java(ZonedDateTime.now())")
    @Mapping(target = "updatedAt", expression = "java(ZonedDateTime.now())")
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User toEntity(UserDto userDto);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "createdAt", source = "createdAt",  dateFormat="dd-MM-yyyy HH:mm:ss")
    @Mapping(target = "updatedAt", source = "updatedAt",  dateFormat="dd-MM-yyyy HH:mm:ss")
    UserDto toDto(User user);

    List<UserDto> toDtos(List<User> users);
}
