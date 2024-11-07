package com.security.poc.dto;

import lombok.Data;

import java.util.List;

@Data
public class PrivilegeAssignDTO {
    private List<Long> privilegeIds;
}
