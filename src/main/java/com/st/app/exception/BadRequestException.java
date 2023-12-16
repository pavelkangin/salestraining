package com.st.app.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends SalesTrainingException {
    public BadRequestException(int errorCode, String errorMessage) {
        super(HttpStatus.BAD_REQUEST, errorCode, errorMessage);
    }
}
