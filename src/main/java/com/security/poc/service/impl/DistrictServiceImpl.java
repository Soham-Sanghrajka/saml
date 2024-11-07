package com.security.poc.service.impl;

import com.security.poc.entity.District;
import com.security.poc.repository.DistrictRepository;
import com.security.poc.service.DistrictService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    private DistrictRepository districtRepository;

    @Override
    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    @Override
    public District getDistrictById(Long id) {
        return districtRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("District not found"));
    }

    @Override
    public District createDistrict(District district) {
        return districtRepository.save(district);
    }

    @Override
    public District updateDistrict(Long id, District districtDetails) {
        District district = getDistrictById(id);
        district.setName(districtDetails.getName());
        return districtRepository.save(district);
    }

    @Override
    public void deleteDistrict(Long id) {
        District district = getDistrictById(id);
        districtRepository.delete(district);
    }
}
