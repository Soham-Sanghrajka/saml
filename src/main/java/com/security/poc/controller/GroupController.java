package com.security.poc.controller;

import com.security.poc.dto.GroupRequestDTO;
import com.security.poc.entity.Group;
import com.security.poc.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private GroupService groupService;


    @PostMapping
    public ResponseEntity<Group> createGroup(@RequestBody GroupRequestDTO groupRequestDTO) {
        Group group = groupService.createGroup(groupRequestDTO);
        return new ResponseEntity<>(group, HttpStatus.CREATED);
    }

    // Get all Groups
    @GetMapping
    public ResponseEntity<List<Group>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return new ResponseEntity<>(groups, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable Long id) {
        Group group = new Group();
        group.setId(id);
        return groupService.getGroupInformation(group);
    }
}
