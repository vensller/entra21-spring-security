package com.senac.spring_auth.dtos;

public class LoginRequest {
    public String email;
    public String password;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}