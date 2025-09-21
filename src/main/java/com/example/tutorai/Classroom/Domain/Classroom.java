package com.example.tutorai.Classroom.Domain;

import com.example.tutorai.Student.Domain.Student;
import com.example.tutorai.Teacher.Domain.Teacher;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Classroom {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    private Teacher teacher;

    private Integer number_students;

    private Set<Student> students


}
