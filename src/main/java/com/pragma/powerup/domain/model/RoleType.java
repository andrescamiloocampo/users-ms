package com.pragma.powerup.domain.model;

public enum RoleType {
    ADMIN("ADMIN"),
    OWNER("OWNER"),
    CUSTOMER("CUSTOMER"),
    EMPLOYEE("EMPLOYEE");

    public final String label;

    private RoleType(String label){
        this.label = label;
    }
}
