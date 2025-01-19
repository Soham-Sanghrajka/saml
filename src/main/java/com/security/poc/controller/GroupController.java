package com.security.poc.controller;

import com.security.poc.dto.GroupRequestDTO;
import com.security.poc.dto.UserGroupRoleRequest;
import com.security.poc.entity.Group;
import com.security.poc.entity.User;
import com.security.poc.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;


    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, null, 'GROUP_CREATE')")
    @PostMapping
    public ResponseEntity<Group> createGroup(@Valid @RequestBody GroupRequestDTO groupRequestDTO) {
        Group group = groupService.createGroup(groupRequestDTO);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, #groupId, 'GROUP_ASSIGN')")
    @PostMapping("/{groupId}/users")
    public ResponseEntity<Void> addUsersToGroup(
            @PathVariable Long groupId,
            @Valid @RequestBody List<UserGroupRoleRequest> userGroupRoleRequests) {
        groupService.addUsersToGroup(groupId, userGroupRoleRequests);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Remove a user from a group
    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, #groupId, 'GROUP_REVOKE')")
    @DeleteMapping("/{groupId}/users/{userId}")
    public ResponseEntity<Void> removeUserFromGroup(@PathVariable Long groupId, @PathVariable Long userId) {
        groupService.removeUserFromGroup(groupId, userId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // Get all groups
    @GetMapping
    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, null, 'GROUP_READ')")
    public List<Group> getGroups(Authentication authentication,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(defaultValue = "id") String sortField,
                                 @RequestParam(defaultValue = "ASC") String sortDirection) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortField));
        User user = (User) authentication.getPrincipal();
        List<Group> groups = groupService.fetchAllGroupsByPermission(user, pageable);
        return groups;
    }


    // Get group by ID
    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, #groupId, 'GROUP_READ')")
    @GetMapping("/{groupId}")
    public ResponseEntity<Group> getGroupById(@PathVariable Long groupId) {
        Group group = groupService.getGroupById(groupId);
        return ResponseEntity.ok(group);
    }
}
