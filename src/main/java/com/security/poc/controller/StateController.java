package com.security.poc.controller;

import com.security.poc.entity.State;
import com.security.poc.service.StateService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/states")
public class StateController {

    @Autowired
    private StateService stateService;

    @GetMapping
    public List<State> getAllStates() {
        return stateService.getAllStates();
    }

    @GetMapping("/{id}")
    public ResponseEntity<State> getState(@PathVariable Long id) {
        State stateById = stateService.getStateById(id);

        return ResponseEntity.ok(stateById);
    }

    @PostMapping
    public State createState(@RequestBody State state) {
        return stateService.createState(state);
    }

    @PutMapping("/{id}")
    public State updateState(@PathVariable Long id, @RequestBody State stateDetails) {
        return stateService.updateState(id, stateDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteState(@PathVariable Long id) {
        stateService.deleteState(id);
    }
}
