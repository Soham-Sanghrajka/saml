package com.security.poc.repository;

import com.security.poc.entity.Group;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    Page<Group> findAll(Pageable pageable);

    // Fetch groups by country ID
    Page<Group> findByDistrict_City_Country_Id(Long id, Pageable pageable);

    // Fetch groups by state and country IDs
    Page<Group> findByDistrict_City_StateIdAndDistrict_City_State_CountryId(Long stateId, Long countryId, Pageable pageable);

    // Fetch groups by city, state, and country IDs
    Page<Group> findByDistrict_CityIdAndDistrict_City_StateIdAndDistrict_City_State_CountryId(Long cityId, Long stateId, Long countryId, Pageable pageable);

    // Fetch groups by district city, state, and country IDs
    Page<Group> findByDistrictIdAndDistrict_CityIdAndDistrict_City_StateIdAndDistrict_City_State_CountryId(Long districtId, Long cityId, Long stateId, Long countryId, Pageable pageable);


}