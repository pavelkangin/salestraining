package com.st.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RegistrationDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("email")
    private String email;
    @JsonProperty("password")
    private String password;

    @Override
    public String toString() {
        return "RegistrationDAO{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
