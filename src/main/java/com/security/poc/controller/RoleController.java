package com.security.poc.controller;

import com.security.poc.dto.PrivilegeAssignDTO;
import com.security.poc.dto.RoleRequestDTO;
import com.security.poc.entity.Role;
import com.security.poc.entity.User;
import com.security.poc.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
@PreAuthorize("hasRole('SUPER_ADMIN')")
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> createRole(@RequestBody RoleRequestDTO roleRequestDTO) {
        Role createdRole = roleService.createRole(roleRequestDTO);
        return new ResponseEntity<>(createdRole, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Role> getRole(@PathVariable Long id) {
        Role role = roleService.getRoleById(id);
        return new ResponseEntity<>(role, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> updateRole(@PathVariable Long id, @RequestBody RoleRequestDTO roleRequestDTO) {
        Role updatedRole = roleService.updateRole(id, roleRequestDTO);
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long id) {
        roleService.deleteRole(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<Role>> getAllRoles() {
        List<Role> roles = roleService.getAllRoles();
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }

    @PostMapping("/{roleId}/privileges")
    public ResponseEntity<Role> assignPrivileges(@PathVariable Long roleId, @RequestBody PrivilegeAssignDTO privilegeAssignDTO) {
        Role updatedRole = roleService.assignPrivilegesToRole(roleId, privilegeAssignDTO.getPrivilegeIds());
        return new ResponseEntity<>(updatedRole, HttpStatus.OK);
    }

    @DeleteMapping("/{roleId}/privileges/{privilegeId}")
    public ResponseEntity<Role> removePrivilege(@PathVariable Long roleId, @PathVariable Long privilegeId) {
        roleService.removePrivilegeFromRole(roleId, privilegeId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{roleId}/users")
    public ResponseEntity<List<User>> getUsersByRole(@PathVariable Long roleId) {
        List<User> users = roleService.getUsersByRole(roleId);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
