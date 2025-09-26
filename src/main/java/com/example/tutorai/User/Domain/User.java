package com.example.tutorai.User.Domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Entity
@Table(name="Users")
@Inheritance(strategy= InheritanceType.JOINED)
public class User{

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    @Column(name="name",nullable=false)
    private String name;
    @Column(name="lastname", nullable=false)
    private String lastNames;
    @Column(name="age", nullable = false)
    private Integer age;
    @Enumerated(EnumType.STRING)
    @Column(name="role",nullable=false)
    private Role role;




}
