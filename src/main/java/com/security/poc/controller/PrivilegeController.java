package com.security.poc.controller;

import com.security.poc.dto.PrivilegeRequestDTO;
import com.security.poc.dto.RoleAssignmentRequestDTO;
import com.security.poc.entity.Privilege;
import com.security.poc.entity.Role;
import com.security.poc.service.PrivilegeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/privileges")
@RequiredArgsConstructor
public class PrivilegeController {

    private final PrivilegeService privilegeService;

    // Create a new Privilege
    @PostMapping
    public ResponseEntity<Privilege> createPrivilege(@RequestBody PrivilegeRequestDTO privilegeRequestDTO) {
        Privilege privilege = privilegeService.createPrivilege(privilegeRequestDTO.getName());
        return new ResponseEntity<>(privilege, HttpStatus.CREATED);
    }

    // Get Privilege by ID
    @GetMapping("/{id}")
    public ResponseEntity<Privilege> getPrivilegeById(@PathVariable Long id) {
        Privilege privilege = privilegeService.getPrivilegeById(id);
        return new ResponseEntity<>(privilege, HttpStatus.OK);
    }

    // Get all Privileges
    @GetMapping
    public ResponseEntity<List<Privilege>> getAllPrivileges() {
        List<Privilege> privileges = privilegeService.getAllPrivileges();
        return new ResponseEntity<>(privileges, HttpStatus.OK);
    }

    // Update Privilege
    @PutMapping("/{id}")
    public ResponseEntity<Privilege> updatePrivilege(@PathVariable Long id, @RequestBody PrivilegeRequestDTO privilegeRequestDTO) {
        Privilege updatedPrivilege = privilegeService.updatePrivilege(id, privilegeRequestDTO.getName());
        return new ResponseEntity<>(updatedPrivilege, HttpStatus.OK);
    }

    // Delete Privilege
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePrivilege(@PathVariable Long id) {
        privilegeService.deletePrivilege(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Assign roles to Privilege
    @PostMapping("/{id}/roles")
    public ResponseEntity<Privilege> assignRolesToPrivilege(@PathVariable Long id, @RequestBody RoleAssignmentRequestDTO roleAssignmentRequestDTO) {
        Privilege privilege = privilegeService.assignRolesToPrivilege(id, roleAssignmentRequestDTO.getRoleIds());
        return new ResponseEntity<>(privilege, HttpStatus.OK);
    }

    // Remove a role from Privilege
    @DeleteMapping("/{privilegeId}/roles/{roleId}")
    public ResponseEntity<Void> removeRoleFromPrivilege(@PathVariable Long privilegeId, @PathVariable Long roleId) {
        privilegeService.removeRoleFromPrivilege(privilegeId, roleId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Get all roles assigned to a Privilege
    @GetMapping("/{id}/roles")
    public ResponseEntity<List<Role>> getRolesByPrivilege(@PathVariable Long id) {
        List<Role> roles = privilegeService.getRolesByPrivilege(id);
        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}