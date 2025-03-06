package com.sezanmahmud.hotelbooking.entity;

public class AuthenticationResponse {

    private String token;

    private String massage;


    public AuthenticationResponse() {
    }

    public AuthenticationResponse(String token, String massage) {
        this.token = token;
        this.massage = massage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
