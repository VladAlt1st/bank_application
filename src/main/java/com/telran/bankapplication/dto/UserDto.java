package com.telran.bankapplication.dto;

import jakarta.validation.constraints.*;
import lombok.Value;

@Value
public class UserDto {

    @Null(message = "Id must be null.")
    String id;

    @NotBlank(message = "Role can not be empty.")
    String role;

    @Null(message = "Status must be null.")
    String status;

    @NotNull(message = "First name can not be null.")
    @Pattern(regexp = "[A-Za-z]+",
            message = "First name must contain only letters of the English alphabet.")
    String firstName;

    @NotNull(message = "Last name can not be null.")
    @Pattern(regexp = "[A-Za-z]+",
            message = "Last name must contain only letters of the English alphabet.")
    String lastName;

    @NotNull(message = "Email can not be null.")
    @Email
    String email;

    @NotNull(message = "Password can not be null.")
    @Pattern(regexp = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})",
            message = "Invalid password.")
    String password;

    @NotNull(message = "Address can not be null.")
    @Pattern(regexp = "[A-Za-z0-9\\s.,\\-'/\\\\]+",
            message = "Address contains invalid characters.")
    String address;

    @NotNull(message = "Phone can not be null.")
    @Pattern(regexp = "^\\+48 \\d{3}-\\d{3}-\\d{3}$",
            message = "Invalid phone number.")
    String phone;

    @Null(message = "Created at must be null.")
    String createdAt;

    @Null(message = "Updated at must be null.")
    String updatedAt;
}
