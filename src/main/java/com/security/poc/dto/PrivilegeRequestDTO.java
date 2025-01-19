package com.security.poc.dto;

import com.security.poc.constant.OperationType;
import com.security.poc.constant.ResourceType;
import lombok.Data;

@Data
public class PrivilegeRequestDTO {
    private String name;
    private ResourceType resourceType;
    private OperationType operation;
}
