package com.security.poc.service;

import com.security.poc.entity.District;

import java.util.List;

public interface DistrictService {

    List<District> getAllDistricts();
    District getDistrictById(Long id);
    District createDistrict(District district);
    District updateDistrict(Long id, District districtDetails);
    void deleteDistrict(Long id);
}
