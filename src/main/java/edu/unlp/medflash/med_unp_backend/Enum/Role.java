package edu.unlp.medflash.med_unp_backend.Enum;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum Role {
    ROLE_ADMIN,
    ROLE_CUSTOMER;

    @JsonCreator
    public static Role fromString(String value) {
        if (value == null) return null;
        
        if (value.equalsIgnoreCase("ADMIN") || value.equalsIgnoreCase("ROLE_ADMIN")) {
            return ROLE_ADMIN;
        }
        if (value.equalsIgnoreCase("CUSTOMER") || value.equalsIgnoreCase("ROLE_CUSTOMER")) {
            return ROLE_CUSTOMER;
        }
        throw new IllegalArgumentException("Invalid role: " + value);
    }

    @JsonValue
    public String toValue() {
        return this.name();
    }
}