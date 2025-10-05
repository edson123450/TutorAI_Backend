package com.example.tutorai.Classroom.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ClassroomDTO {

    private Long id;
    private String name;
    private Integer capacity;
    private Integer actualNumberStudents;

}
