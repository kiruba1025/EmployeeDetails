package com.emp.details.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emp.details.entity.State;
import com.emp.details.repository.StateRepository;

@Service
public class StateService {

    @Autowired
    private StateRepository stateRepository;

    public List<State> getStatesByCountryId(Long countryId) {
        return stateRepository.findByCountryId(countryId);
    }

}
