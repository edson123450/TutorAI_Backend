package com.example.tutorai.Teacher.Domain;

import com.example.tutorai.Classroom.Domain.Classroom;
import com.example.tutorai.User.Domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="Teachers")
public class Teacher extends User {

    @Column(name="email",nullable=false)
    private String email;
    @Column(name="password",nullable=false)
    private String password;
    @OneToMany(mappedBy="teacher", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<Classroom> classrooms=new HashSet<>();

}
