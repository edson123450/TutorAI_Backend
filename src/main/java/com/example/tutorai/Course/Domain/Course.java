package com.example.tutorai.Course.Domain;


import com.example.tutorai.Classroom.Domain.Classroom;
import com.example.tutorai.Topic.Domain.Topic;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="Courses", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Course {

    @Id
    @GeneratedValue(strategy= GenerationType.SEQUENCE)
    private Long id;
    @Column(name="name",nullable=false, unique = true)
    private String name;

    @ManyToMany(mappedBy="courses")
    private Set<Classroom> classrooms=new HashSet<>();

    @OneToMany(mappedBy="course", cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<Topic> topics=new HashSet<>();



}
