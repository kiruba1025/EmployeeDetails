package com.emp.details.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.details.entity.State;
import com.emp.details.service.StateService;

@RestController
@RequestMapping("/states")
public class StateController {

    @Autowired
    private StateService stateService;
    
    @GetMapping("/country/{countryId}")
    public ResponseEntity<List<State>> getStatesByCountryId(@PathVariable Long countryId) {
        List<State> states = stateService.getStatesByCountryId(countryId);
        return new ResponseEntity<>(states, HttpStatus.OK);
    
}
}