package com.security.poc.service;

import com.security.poc.dto.GroupRequestDTO;
import com.security.poc.dto.UserGroupRoleRequest;
import com.security.poc.entity.Group;
import com.security.poc.entity.User;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface GroupService {

    Group createGroup(GroupRequestDTO groupRequestDTO);

    void removeUserFromGroup(Long groupId, Long userId);

    Group getGroupById(Long groupId);

    void addUsersToGroup(Long groupId, @Valid List<UserGroupRoleRequest> userGroupRoleRequests);

    List<Group> fetchAllGroupsByPermission(User user, Pageable pageable);
}