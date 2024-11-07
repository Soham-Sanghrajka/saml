package com.security.poc.service;

import com.security.poc.entity.Privilege;
import com.security.poc.entity.Role;

import java.util.List;

public interface PrivilegeService {
    Privilege createPrivilege(String name);
    Privilege getPrivilegeById(Long id);
    List<Privilege> getAllPrivileges();
    Privilege updatePrivilege(Long id, String name);
    void deletePrivilege(Long id);
    Privilege assignRolesToPrivilege(Long privilegeId, List<Long> roleIds);
    void removeRoleFromPrivilege(Long privilegeId, Long roleId);
    List<Role> getRolesByPrivilege(Long privilegeId);

}
