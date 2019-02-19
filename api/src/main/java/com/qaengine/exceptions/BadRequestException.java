package com.qaengine.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {
    public BadRequestException() {
        super("HTTP request parameters/payload is invalid");
    }

    public BadRequestException(String message) {
        super(message);
    }
}
