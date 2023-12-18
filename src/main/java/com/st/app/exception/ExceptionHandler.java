package com.st.app.exception;

import com.st.app.dto.DefaultResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
public class ExceptionHandler {
    @org.springframework.web.bind.annotation.ExceptionHandler (SalesTrainingException.class)
    public ResponseEntity<DefaultResponse> handleSalesTrainingException (SalesTrainingException e){
        DefaultResponse response = new DefaultResponse();
        response.setStatus(e.getErrorCode());
        response.setMessage(e.getMessage());
        return ResponseEntity.status(e.getStatus()).body(response);
    }
    @org.springframework.web.bind.annotation.ExceptionHandler (Exception.class)
    public ResponseEntity<DefaultResponse> handleException (Exception e){
        DefaultResponse response = new DefaultResponse();
        HttpStatus status = (e instanceof ResponseStatusException)
                ? ((ResponseStatusException) e).getStatus()
                : HttpStatus.INTERNAL_SERVER_ERROR;
        response.setStatus(status.value());
        response.setMessage(e.getMessage());
        return ResponseEntity.status(status).body(response);
    }
}
