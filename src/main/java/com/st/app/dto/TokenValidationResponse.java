package com.st.app.dto;

public class TokenValidationResponse {
    private boolean success;
    private String[] error_codes;

    public boolean isSuccess() {
        return success;
    }

    public String[] getError_codes() {
        return error_codes;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
