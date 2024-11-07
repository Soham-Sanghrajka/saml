package com.security.poc.dto;


import lombok.Data;

import java.util.List;

@Data
public class RoleRequestDTO {
    private String name;
    private List<Long> privilegeIds;
}
