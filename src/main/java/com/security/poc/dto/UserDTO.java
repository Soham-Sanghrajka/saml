package com.security.poc.dto;

import com.security.poc.entity.City;
import com.security.poc.entity.Country;
import com.security.poc.entity.District;
import com.security.poc.entity.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String primaryRole;
    private String groupRole;
    private Country country;
    private State state;
    private City city;
    private District district;
}
