package com.pragma.powerup.infrastructure.exceptionhandler;

import lombok.Getter;

@Getter
public enum ExceptionResponse {
    NO_DATA_FOUND("No data found for the requested petition"),
    USER_ALREADY_EXISTS("User already exists"),
    ROLE_ALREADY_EXISTS("Role already exists"),
    INVALID_DOCUMENT("The provided identity document is not valid"),
    INVALID_EMAIL("The provided email address is not valid"),
    INVALID_PHONE("The provided phone number is not valid"),
    UNDERAGE("User must be of legal age"),
    DOMAIN_EXCEPTION("A domain error has occurred");

    private final String message;

    ExceptionResponse(String message) {
        this.message = message;
    }

}
