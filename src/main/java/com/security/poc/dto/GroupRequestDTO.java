package com.security.poc.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupRequestDTO {

    private String groupName;
    private Long districtId;
    private List<UserGroupRoleRequest> userGroupRoles;
}