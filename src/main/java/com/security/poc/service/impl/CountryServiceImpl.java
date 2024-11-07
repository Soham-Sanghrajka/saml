package com.security.poc.service.impl;

import com.security.poc.entity.Country;
import com.security.poc.repository.CountryRepository;
import com.security.poc.service.CountryService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryServiceImpl implements CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Country getCountryById(Long id) {
        return countryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found"));
    }

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country updateCountry(Long id, Country countryDetails) {
        Country country = getCountryById(id);
        country.setName(countryDetails.getName());
        return countryRepository.save(country);
    }

    public void deleteCountry(Long id) {
        Country country = getCountryById(id);
        countryRepository.delete(country);
    }
}
