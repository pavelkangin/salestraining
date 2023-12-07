package com.st.app.exception;

import org.springframework.http.HttpStatus;

public class SalesTrainingException extends RuntimeException {
    private HttpStatus status;

    public SalesTrainingException(HttpStatus status, String errorMessage) {
        super (errorMessage);
        this.status = status;
    }
    public SalesTrainingException(HttpStatus status, String errorMessage, Throwable e) {
        super(errorMessage, e);
        this.status = status;
    }
    public HttpStatus getStatus() {
        return status;
    }
}

