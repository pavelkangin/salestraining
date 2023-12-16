package com.st.app.exception;

import org.springframework.http.HttpStatus;

public class SalesTrainingException extends RuntimeException {
    private HttpStatus status;
    private int errorCode;

    public SalesTrainingException(HttpStatus status, int errorCode, String errorMessage) {
        super (errorMessage);
        this.status = status;
        this.errorCode = errorCode;
    }
    public SalesTrainingException(HttpStatus status, int errorCode, String errorMessage, Throwable e) {
        super(errorMessage, e);
        this.status = status;
        this.errorCode = errorCode;
    }
    public HttpStatus getStatus() {
        return status;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

