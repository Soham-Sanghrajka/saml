package com.security.poc.service;

import com.security.poc.dto.RoleRequestDTO;
import com.security.poc.entity.Role;
import com.security.poc.entity.User;

import java.util.List;

public interface RoleService {

    Role createRole(RoleRequestDTO roleRequestDTO);

    Role getRoleById(Long id);

    Role updateRole(Long id, RoleRequestDTO roleRequestDTO);

    void deleteRole(Long id);

    List<Role> getAllRoles();

    Role assignPrivilegesToRole(Long roleId, List<Long> privilegeIds);

    void removePrivilegeFromRole(Long roleId, Long privilegeId);

    List<User> getUsersByRole(Long roleId);
}
