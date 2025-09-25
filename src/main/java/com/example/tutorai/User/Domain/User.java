package com.example.tutorai.User.Domain;


import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@Entity
@Table(name="Users")
@Inheritance(strategy= InheritanceType.JOINED)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    @Column(name="name",nullable=false)
    private String name;
    @Column(name="lastname", nullable=false)
    private String lastNames;
    @Column(name="age", nullable = false)
    private Integer age;
    @Column(name="role",nullable=false)
    private Role role;





}
