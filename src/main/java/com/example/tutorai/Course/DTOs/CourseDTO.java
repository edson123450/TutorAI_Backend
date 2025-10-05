package com.example.tutorai.Course.DTOs;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CourseDTO {
    private Long courseId;
    private String name;

    public CourseDTO(Long courseId, String name){
        this.courseId=courseId;
        this.name=name;
    }
}
