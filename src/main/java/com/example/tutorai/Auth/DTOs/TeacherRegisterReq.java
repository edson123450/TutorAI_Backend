package com.example.tutorai.Auth.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TeacherRegisterReq {
    private String name;
    private String lastNames;
    private Integer age;
    private String email;
    private String password;
}
