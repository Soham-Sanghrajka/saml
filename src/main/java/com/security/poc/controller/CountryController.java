package com.security.poc.controller;

import com.security.poc.entity.Country;
import com.security.poc.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/countries")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping
    @PreAuthorize("hasPermission(null, 'COUNTRY_READ') or hasRole('SUPER_ADMIN')")
    public List<Country> getAllCountries() {
        return countryService.getAllCountries();
    }

    @GetMapping("/{id}")
    @PreAuthorize("@permissionEvaluator.hasPermission(authentication, null, 'COUNTRY_READ') or hasRole('SUPER_ADMIN')")
    public Country getCountry(@PathVariable Long id) {
        return countryService.getCountryById(id);
    }

    @PostMapping
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Country createCountry(@RequestBody Country country) {
        return countryService.createCountry(country);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public Country updateCountry(@PathVariable Long id, @RequestBody Country countryDetails) {
        return countryService.updateCountry(id, countryDetails);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public void deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
    }
}
