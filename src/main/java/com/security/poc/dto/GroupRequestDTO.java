package com.security.poc.dto;

import lombok.Data;

import java.util.List;

@Data
public class GroupRequestDTO {

    private String name;
    private Long countryAdmin;
    private Long stateAdmin;
    private Long cityAdmin;
    private Long districtAdmin;
    private Boolean status;
    private List<Long> userIds;
    private Long districtId;
}
