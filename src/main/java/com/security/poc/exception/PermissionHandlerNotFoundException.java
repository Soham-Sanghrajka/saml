package com.security.poc.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PermissionHandlerNotFoundException extends RuntimeException {

    public PermissionHandlerNotFoundException(Class<?> targetClass) {
        super("No permission handler found for class: " + targetClass.getName());
    }
}
