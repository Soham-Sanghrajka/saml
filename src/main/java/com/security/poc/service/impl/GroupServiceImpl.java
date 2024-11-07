package com.security.poc.service.impl;

import com.security.poc.dto.GroupRequestDTO;
import com.security.poc.entity.District;
import com.security.poc.entity.Group;
import com.security.poc.entity.User;
import com.security.poc.repository.GroupRepository;
import com.security.poc.service.DistrictService;
import com.security.poc.service.GroupService;
import com.security.poc.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private GroupRepository groupRepository;
    private UserService userService;
    private DistrictService districtService;

    @Override
    public Group createGroup(GroupRequestDTO groupRequestDTO) {
        Group group = new Group();
        List<User> usersList = userService.allUsers();

        boolean checkCountryAdmin = usersList.stream().anyMatch(user -> user.getId().equals(groupRequestDTO.getCountryAdmin()));
        if (checkCountryAdmin) {
            group.setCountryAdmin(groupRequestDTO.getCountryAdmin());
        }

        boolean checkStateAdmin = usersList.stream().anyMatch(user -> user.getId().equals(groupRequestDTO.getStateAdmin()));
        if (checkStateAdmin) {
            group.setStateAdmin(groupRequestDTO.getStateAdmin());
        }

        boolean checkCityAdmin = usersList.stream().anyMatch(user -> user.getId().equals(groupRequestDTO.getCityAdmin()));
        if (checkCityAdmin) {
            group.setCityAdmin(groupRequestDTO.getCityAdmin());
        }

        boolean checkDistrictAdmin = usersList.stream().anyMatch(user -> user.getId().equals(groupRequestDTO.getDistrictAdmin()));
        if (checkDistrictAdmin) {
            group.setDistrictAdmin(groupRequestDTO.getDistrictAdmin());
        }

        List<User> groupUsersList = usersList.stream()
                .filter(user -> groupRequestDTO.getUserIds().contains(user.getId())).toList();

        District districtById = districtService.getDistrictById(groupRequestDTO.getDistrictId());

        group.setStatus(groupRequestDTO.getStatus());
        group.setName(groupRequestDTO.getName());
        group.setUsers(groupUsersList);
        group.setDistrict(districtById);

        return groupRepository.save(group);
    }

    @Override
    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    @Override
    public Group getGroupInformation(Group group) {
        return groupRepository.findById(group.getId())
                .orElseThrow(() -> new EntityNotFoundException("Group not found"));
    }
}
