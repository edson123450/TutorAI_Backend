package com.example.tutorai.Teacher.Domain;

import com.example.tutorai.User.Domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Teacher extends User {

    @Column(name="email",nullable=false)
    private String email;
    @Column(name="password",nullable=false)
    private String password;

}
