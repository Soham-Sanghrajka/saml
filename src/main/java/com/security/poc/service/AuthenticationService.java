package com.security.poc.service;

import com.security.poc.dto.LoginUserDto;
import com.security.poc.dto.RegisterUserDto;
import com.security.poc.entity.User;

public interface AuthenticationService {

    User signup(RegisterUserDto input);
    User authenticate(LoginUserDto input);
}
