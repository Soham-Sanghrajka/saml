package com.security.poc.service.impl;

import com.security.poc.entity.City;
import com.security.poc.repository.CityRepository;
import com.security.poc.service.CityService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public City getCityById(Long id) {
        return cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City not found"));
    }

    @Override
    public List<City> getCitiesByStateId(Long stateId) {
        return cityRepository.findByState_Id(stateId);
    }

    @Override
    public City createCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public City updateCity(Long id, City cityDetails) {
        City city = getCityById(id);
        city.setName(cityDetails.getName());
        return cityRepository.save(city);
    }

    @Override
    public void deleteCity(Long id) {
        City city = getCityById(id);
        cityRepository.delete(city);
    }
}
