package com.security.poc.service;

import com.security.poc.entity.City;

import java.util.List;

public interface CityService {
    List<City> getAllCities();
    City getCityById(Long id);
    City createCity(City city);
    City updateCity(Long id, City cityDetails);
    void deleteCity(Long id);

}
