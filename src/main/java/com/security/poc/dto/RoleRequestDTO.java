package com.security.poc.dto;


import lombok.Data;

import java.util.Set;

@Data
public class RoleRequestDTO {
    private String name;
    private Set<Long> privilegeIds;
}
