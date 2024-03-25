package com.emp.details.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.emp.details.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}
