package com.security.poc.service;

import com.security.poc.entity.Country;

import java.util.List;

public interface CountryService {

    List<Country> getAllCountries();
    Country getCountryById(Long id);
    Country createCountry(Country country);
    Country updateCountry(Long id, Country countryDetails);
    void deleteCountry(Long id);
}
