package com.group.autoconfienceback.exceptions;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException {
    private String message;
    private final int responseCode;

    public ApiException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

}
