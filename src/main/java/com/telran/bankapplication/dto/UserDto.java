package com.telran.bankapplication.dto;

import lombok.Value;

@Value
public class UserDto {
    String role;
    String status;
    String taxCode;
    String firstName;
    String lastName;
    String email;
    String password;
    String address;
    String phone;
}
