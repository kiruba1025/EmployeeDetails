package com.emp.details.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.emp.details.entity.Country;
import com.emp.details.service.CountryService;
@RestController
@RequestMapping("/countries")
public class CountryController {
	@Autowired
	CountryService countryService;
	 @GetMapping
	    public ResponseEntity<List<Country>> getAllCountries() {
	        List<Country> countries = countryService.getAllCountries();
	        return new ResponseEntity<>(countries, HttpStatus.OK);
	    }
}
