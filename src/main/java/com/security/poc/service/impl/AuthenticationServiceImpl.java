package com.security.poc.service.impl;

import com.security.poc.dto.LoginUserDto;
import com.security.poc.dto.RegisterUserDto;
import com.security.poc.entity.Role;
import com.security.poc.entity.User;
import com.security.poc.repository.UserRepository;
import com.security.poc.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CountryService countryService;
    private final StateService stateService;
    private final RoleService roleService;
    private final CityService cityService;
    private final DistrictService districtService;


    @Override
    public User signup(RegisterUserDto input) {
        User user = new User();

        user.setFullName(input.getFullName());
        user.setUsername(input.getUsername());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setCountry(countryService.getCountryById(input.getCountryId()));
        user.setState(stateService.getStateById(input.getStateId()));
        user.setCity(cityService.getCityById(input.getCityId()));
        user.setDistrict(districtService.getDistrictById(input.getDistrictId()));

        List<Role> list = input.getRoleIds().stream().map(roleService::getRoleById).toList();
        user.setRoles(list);

        return userRepository.save(user);
    }

    @Override
    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getUsername(),
                        input.getPassword()
                )
        );

        return userRepository.findByUsername(input.getUsername())
                .orElseThrow();
    }
}
