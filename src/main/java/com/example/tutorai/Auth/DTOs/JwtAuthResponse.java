package com.example.tutorai.Auth.DTOs;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtAuthResponse {
    private String token;
    public JwtAuthResponse(){}
    public JwtAuthResponse(String token){
        this.token=token;
    }
}
