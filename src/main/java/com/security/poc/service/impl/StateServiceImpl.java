package com.security.poc.service.impl;

import com.security.poc.entity.State;
import com.security.poc.repository.StateRepository;
import com.security.poc.service.StateService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StateServiceImpl implements StateService {

    @Autowired
    private StateRepository stateRepository;

    @Override
    public List<State> getAllStates() {
        return stateRepository.findAll();
    }

    @Override
    public State getStateById(Long id) {
        return stateRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("State not found"));
    }

    @Override
    public State createState(State state) {
        return stateRepository.save(state);
    }

    @Override
    public State updateState(Long id, State stateDetails) {
        State state = getStateById(id);
        state.setName(stateDetails.getName());
        return stateRepository.save(state);
    }

    @Override
    public void deleteState(Long id) {
        State state = getStateById(id);
        stateRepository.delete(state);
    }
}
