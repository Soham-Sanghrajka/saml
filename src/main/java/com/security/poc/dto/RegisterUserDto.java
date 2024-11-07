package com.security.poc.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class RegisterUserDto {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @Email(message = "Valid email is required")
    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @NotNull(message = "role is required")
    private List<Long> roleIds;

    @NotBlank(message = "Primary role is required")
    private String primaryRole;

    @NotBlank(message = "Group role is required")
    private String groupRole;

    @NotNull(message = "Country ID is required")
    private Long countryId;

    @NotNull(message = "State ID is required")
    private Long stateId;

    @NotNull(message = "City ID is required")
    private Long cityId;

    @NotNull(message = "District ID is required")
    private Long districtId;
}
