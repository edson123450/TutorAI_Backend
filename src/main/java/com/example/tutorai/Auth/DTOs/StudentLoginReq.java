package com.example.tutorai.Auth.DTOs;

import lombok.Data;

@Data
public class StudentLoginReq {
    private String name;
    private String lastNames;
    private String passwordNumber;
}
