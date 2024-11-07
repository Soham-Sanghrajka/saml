package com.security.poc.service;

import com.security.poc.dto.GroupRequestDTO;
import com.security.poc.entity.Group;

import java.util.List;

public interface GroupService {

    Group getGroupInformation(Group group);
    Group createGroup(GroupRequestDTO groupRequestDTO);
    List<Group> getAllGroups();


}