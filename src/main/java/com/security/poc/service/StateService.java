package com.security.poc.service;

import com.security.poc.entity.State;

import java.util.List;

public interface StateService {
    List<State> getAllStates();
    State getStateById(Long id);
    State createState(State state);
    State updateState(Long id, State stateDetails);
    void deleteState(Long id);
}
