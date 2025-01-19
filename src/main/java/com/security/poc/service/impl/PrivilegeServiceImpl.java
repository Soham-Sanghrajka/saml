package com.security.poc.service.impl;

import com.security.poc.constant.OperationType;
import com.security.poc.constant.ResourceType;
import com.security.poc.dto.PrivilegeRequestDTO;
import com.security.poc.entity.Privilege;
import com.security.poc.entity.Role;
import com.security.poc.exception.ResourceNotFoundException;
import com.security.poc.repository.PrivilegeRepository;
import com.security.poc.repository.RoleRepository;
import com.security.poc.service.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PrivilegeServiceImpl implements PrivilegeService {
    private final PrivilegeRepository privilegeRepository;
    private final RoleRepository roleRepository;

    @Override
    public Privilege createPrivilege(PrivilegeRequestDTO privilegeRequest) {
        Privilege privilege = new Privilege();
        privilege.setName(generatePermissionName(privilegeRequest.getResourceType(), privilegeRequest.getOperation()));
        privilege.setResourceType(privilegeRequest.getResourceType());
        privilege.setOperation(privilegeRequest.getOperation());
        return privilegeRepository.save(privilege);
    }

    private String generatePermissionName(ResourceType resourceType, OperationType operation) {
        return resourceType.name() + "_" + operation.name();
    }

    @Override
    public Privilege getPrivilegeById(Long id) {
        return privilegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with id " + id));
    }

    @Override
    public List<Privilege> getAllPrivileges() {
        return privilegeRepository.findAll();
    }

    @Override
    public Privilege updatePrivilege(Long id, String name) {
        Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with id " + id));

        privilege.setName(name);
        return privilegeRepository.save(privilege);
    }

    @Override
    public void deletePrivilege(Long id) {
        Privilege privilege = privilegeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with id " + id));

        privilegeRepository.delete(privilege);
    }

    @Override
    public Privilege assignRolesToPrivilege(Long privilegeId, List<Long> roleIds) {
        Privilege privilege = privilegeRepository.findById(privilegeId)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with id " + privilegeId));

        Collection<Role> roles = roleRepository.findAllById(roleIds);

        return privilegeRepository.save(privilege);
    }

    @Override
    public void removeRoleFromPrivilege(Long privilegeId, Long roleId) {
        Privilege privilege = privilegeRepository.findById(privilegeId)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with id " + privilegeId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));

        privilegeRepository.save(privilege);
    }

    @Override
    public List<Role> getRolesByPrivilege(Long privilegeId) {
        Privilege privilege = privilegeRepository.findById(privilegeId)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with id " + privilegeId));

        return new ArrayList<>();
    }
}
