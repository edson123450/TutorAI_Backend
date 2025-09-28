package com.example.tutorai.User.Domain;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@Entity
@Table(name="Users", uniqueConstraints = @UniqueConstraint(columnNames={"name","lastnames"}),
        indexes={@Index(name="idx_students_name_lastnames",columnList="name, lastnames")})
@Inheritance(strategy= InheritanceType.JOINED)
public class User{

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    @Column(name="name",nullable=false)
    private String name;
    @Column(name="lastnames", nullable=false)
    private String lastNames;
    @Column(name="age", nullable = false)
    private Integer age;
    @Enumerated(EnumType.STRING)
    @Column(name="role",nullable=false)
    private Role role;




}
