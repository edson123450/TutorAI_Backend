package com.example.tutorai.Exercise.DTOs;

import lombok.Data;

@Data
public class GetExercisesByLevelDTO {
    private Long classroomId;
    private Long courseId;
    private Integer topicNumber;
    private Integer levelNumber;
}
