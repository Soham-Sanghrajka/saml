package com.security.poc.security;


import com.security.poc.constant.RoleConstants;
import com.security.poc.dto.UserDTO;
import com.security.poc.entity.Group;
import com.security.poc.entity.User;
import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class GeographicPermissionEvaluator implements PermissionEvaluator {


    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        if (authentication == null || !(permission instanceof String)) {
            return false;
        }

        User user = (User) authentication.getPrincipal(); // Assume your custom User class
        String permissionStr = (String) permission;

        if ("access_group".equals(permissionStr)) {
            return checkUserCanAccessGroup(user, targetDomainObject);
        }

        if ("access_user_details".equals(permissionStr)) {
            return checkUserCanAccessUserDetails(user, targetDomainObject);
        }
        return false;
    }

    private boolean checkUserCanAccessGroup(User user, Object group) {
        Group groupDto = (Group) group;

        // Country admin can access groups only within their country
        if ("ROLE_COUNTRY_ADMIN".equals(user.getGroupRole()) && user.getCountry().getName().equals(groupDto.getDistrict().getCity().getCountry().getName())) {
            return true;
        }

        // State admin can access groups within their state and country only
        if ("ROLE_STATE_ADMIN".equals(user.getGroupRole())
                && user.getState().getName().equals(groupDto.getDistrict().getCity().getState().getName())
                && user.getCountry().getName().equals(groupDto.getDistrict().getCity().getCountry().getName())) {
            return true;
        }

        // City admin can access groups within their city, state, and country only
        if (RoleConstants.ROLE_CITY_ADMIN.equals(user.getGroupRole())
                && user.getCity().getName().equals(groupDto.getDistrict().getCity().getCountry().getName())
                && user.getState().getName().equals(groupDto.getDistrict().getCity().getState().getName())
                && user.getCountry().getName().equals(groupDto.getDistrict().getCity().getCountry().getName())) {
            return true;
        }

        // District admin can access groups within their district, city, state, and country only
        if (RoleConstants.ROLE_DISTRICT_ADMIN.equals(user.getGroupRole())
                && user.getDistrict().equals(groupDto.getDistrict())
                && user.getCity().getName().equals(groupDto.getDistrict().getCity().getCountry().getName())
                && user.getState().getName().equals(groupDto.getDistrict().getCity().getState().getName())
                && user.getCountry().getName().equals(groupDto.getDistrict().getCity().getCountry().getName())) {
            return true;
        }

        // Deny access if no conditions are met
        return false;
    }

    private boolean checkUserCanAccessUserDetails(User user, Object targetUser) {
        UserDTO targetUserDto = (UserDTO) targetUser;

        // Only allow access if both users are in the same country, state, and city (if needed)
        if (RoleConstants.ROLE_COUNTRY_ADMIN.equals(user.getGroupRole()) && user.getCountry().getName().equals(targetUserDto.getCountry().getName())) {
            return true;
        }

        if (RoleConstants.ROLE_STATE_ADMIN.equals(user.getGroupRole())
                && user.getState().equals(targetUserDto.getState())
                && user.getCountry().equals(targetUserDto.getCountry())) {
            return true;
        }

        if (RoleConstants.ROLE_CITY_ADMIN.equals(user.getGroupRole())
                && user.getCity().equals(targetUserDto.getCity())
                && user.getState().equals(targetUserDto.getState())
                && user.getCountry().equals(targetUserDto.getCountry())) {
            return true;
        }

        if (RoleConstants.ROLE_DISTRICT_ADMIN.equals(user.getGroupRole())
                && user.getDistrict().equals(targetUserDto.getDistrict())
                && user.getCity().equals(targetUserDto.getCity())
                && user.getState().equals(targetUserDto.getState())
                && user.getCountry().equals(targetUserDto.getCountry())) {
            return true;
        }

        return false;
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType, Object permission) {
        return false;
    }
}
