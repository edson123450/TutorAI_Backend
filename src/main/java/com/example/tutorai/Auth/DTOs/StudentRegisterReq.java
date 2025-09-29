package com.example.tutorai.Auth.DTOs;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentRegisterReq {
    private String name;
    private String lastNames;
    private Integer age;
    private String passwordNumber;
}
