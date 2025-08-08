package com.senac.spring_auth.dtos;

public class LoginResponse {
    public String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
