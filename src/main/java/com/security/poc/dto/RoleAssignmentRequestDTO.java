package com.security.poc.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleAssignmentRequestDTO {
    private List<Long> roleIds;
}
