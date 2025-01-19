package com.security.poc.service.impl;

import com.security.poc.constant.RoleConstants;
import com.security.poc.dto.GroupRequestDTO;
import com.security.poc.dto.UserGroupRoleRequest;
import com.security.poc.entity.*;
import com.security.poc.repository.GroupRepository;
import com.security.poc.repository.UserGroupRoleRepository;
import com.security.poc.service.DistrictService;
import com.security.poc.service.GroupService;
import com.security.poc.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {

    private final GroupRepository groupRepository;
    private final UserService userService;
    private final DistrictService districtService;
    private final UserGroupRoleRepository userGroupRoleRepository;

    @Override
    public Group createGroup(GroupRequestDTO groupRequest) {
        District district = districtService.getDistrictById(groupRequest.getDistrictId());
        List<User> userList = userService.allUsers();

        Group group = new Group();
        group.setGroupName(groupRequest.getGroupName());
        group.setDistrict(district);
        group.setStatus(Boolean.TRUE);


        List<UserGroupRole> userGroupRoles = new ArrayList<>();
        if (groupRequest.getUserGroupRoles() != null) {
            for (UserGroupRoleRequest userRequest : groupRequest.getUserGroupRoles()) {
                User user = userList.stream()
                        .filter(u -> u.getId().equals(userRequest.getUserId()))
                        .findFirst()
                        .orElseThrow(() -> new RuntimeException("User with ID " + userRequest.getUserId() + " not found"));

                UserGroupRole userGroupRole = new UserGroupRole();
                userGroupRole.setGroup(group);
                userGroupRole.setUser(user);
                userGroupRoles.add(userGroupRole);
            }
        }

        group.setUserGroupRoles(userGroupRoles);

        return groupRepository.save(group);
    }

    @Override
    public void addUsersToGroup(Long groupId, List<UserGroupRoleRequest> userGroupRoleRequests) {
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        for (UserGroupRoleRequest userRequest : userGroupRoleRequests) {
            User user = userService.findById(userRequest.getUserId());

            UserGroupRole userGroupRole = new UserGroupRole();
            userGroupRole.setGroup(group);
            userGroupRole.setUser(user);

            userGroupRoleRepository.save(userGroupRole);
        }
    }

    @Override
    public List<Group> fetchAllGroupsByPermission(User user, Pageable pageable) {
        // Fetch roles and determine the query logic
        List<String> userRoles = user.getRoles().stream().map(Role::getName).toList();

        if (userRoles.contains(RoleConstants.ROLE_SUPER_ADMIN)) {
            // Super Admin or Admin can see all groups
            return groupRepository.findAll(pageable).getContent();
        }

        if (userRoles.contains(RoleConstants.ROLE_COUNTRY_ADMIN)) {
            return groupRepository.findByDistrict_City_Country_Id(user.getCountry().getId(), pageable).getContent();
        }

        if (userRoles.contains(RoleConstants.ROLE_STATE_ADMIN)) {
            return groupRepository.findByDistrict_City_StateIdAndDistrict_City_State_CountryId(user.getState().getId(), user.getCountry().getId(), pageable).getContent();
        }

        if (userRoles.contains(RoleConstants.ROLE_CITY_ADMIN)) {
            return groupRepository.findByDistrict_CityIdAndDistrict_City_StateIdAndDistrict_City_State_CountryId(user.getCity().getId(), user.getState().getId(), user.getCountry().getId(), pageable).getContent();
        }

        if (userRoles.contains(RoleConstants.ROLE_DISTRICT_ADMIN)) {
            return groupRepository.findByDistrictIdAndDistrict_CityIdAndDistrict_City_StateIdAndDistrict_City_State_CountryId(
                    user.getDistrict().getId(),
                    user.getCity().getId(),
                    user.getState().getId(),
                    user.getCountry().getId(),
                    pageable).getContent();
        }

        return List.of(); // If no permission found, return empty list
    }

    @Override
    public void removeUserFromGroup(Long groupId, Long userId) {
        UserGroupRole userGroupRole = userGroupRoleRepository.findByGroupIdAndUserId(groupId, userId)
                .orElseThrow(() -> new RuntimeException("UserGroupRole not found"));
        userGroupRoleRepository.delete(userGroupRole);
    }

    @Override
    public Group getGroupById(Long groupId) {
        return groupRepository.findById(groupId)
                .orElseThrow(() -> new RuntimeException("Group not found"));
    }
}