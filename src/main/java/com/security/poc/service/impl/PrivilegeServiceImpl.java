package com.security.poc.service.impl;

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
    public Privilege createPrivilege(String name) {
        Privilege privilege = new Privilege();
        privilege.setName(name);
        return privilegeRepository.save(privilege);
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
        privilege.getRoles().addAll(roles);

        return privilegeRepository.save(privilege);
    }

    @Override
    public void removeRoleFromPrivilege(Long privilegeId, Long roleId) {
        Privilege privilege = privilegeRepository.findById(privilegeId)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with id " + privilegeId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with id " + roleId));

        privilege.getRoles().remove(role);

        privilegeRepository.save(privilege);
    }

    @Override
    public List<Role> getRolesByPrivilege(Long privilegeId) {
        Privilege privilege = privilegeRepository.findById(privilegeId)
                .orElseThrow(() -> new ResourceNotFoundException("Privilege not found with id " + privilegeId));

        return new ArrayList<>(privilege.getRoles());
    }
}
