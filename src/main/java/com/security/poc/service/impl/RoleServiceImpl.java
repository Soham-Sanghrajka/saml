package com.security.poc.service.impl;

import com.security.poc.dto.RoleRequestDTO;
import com.security.poc.entity.Privilege;
import com.security.poc.entity.Role;
import com.security.poc.entity.User;
import com.security.poc.exception.ResourceNotFoundException;
import com.security.poc.repository.PrivilegeRepository;
import com.security.poc.repository.RoleRepository;
import com.security.poc.repository.UserRepository;
import com.security.poc.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final PrivilegeRepository privilegeRepository;
    private final UserRepository userRepository;

    @Override
    public Role createRole(RoleRequestDTO roleRequestDTO) {
        Role role = new Role();
        role.setName(roleRequestDTO.getName());

        Optional.ofNullable(roleRequestDTO.getPrivilegeIds())
                .filter(privilegeIds -> !privilegeIds.isEmpty()) // Filter out empty lists
                .ifPresent(privilegeIds -> {
                    // Fetch privileges and ensure they exist before assigning
                    Set<Privilege> privileges = new HashSet<>(privilegeRepository.findAllById(privilegeIds));
                    role.setPrivileges(privileges);
                });

        return roleRepository.save(role);
    }

    @Override
    public Role getRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
    }

    @Override
    public Role updateRole(Long id, RoleRequestDTO roleRequestDTO) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));

        role.setName(roleRequestDTO.getName());

        if (roleRequestDTO.getPrivilegeIds() != null && !roleRequestDTO.getPrivilegeIds().isEmpty()) {
            Collection<Privilege> privileges = privilegeRepository.findAllById(roleRequestDTO.getPrivilegeIds());
            role.setPrivileges((Set<Privilege>) privileges);
        }

        return roleRepository.save(role);
    }

    @Override
    public void deleteRole(Long id) {
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + id));
        roleRepository.delete(role);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role assignPrivilegesToRole(Long roleId, List<Long> privilegeIds) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));

        Collection<Privilege> privileges = privilegeRepository.findAllById(privilegeIds);
        role.getPrivileges().addAll(privileges);

        return roleRepository.save(role);
    }

    @Override
    public void removePrivilegeFromRole(Long roleId, Long privilegeId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));

        Privilege privilege = privilegeRepository.findById(privilegeId)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with id " + privilegeId));

        role.getPrivileges().remove(privilege);

        roleRepository.save(role);
    }

    @Override
    public List<User> getUsersByRole(Long roleId) {
        return userRepository.findAllByRoles_Id(roleId);
    }
}
