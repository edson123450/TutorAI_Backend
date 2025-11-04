package com.example.tutorai.Exercise.DTOs;

import lombok.Data;

@Data
public class ExerciseCreateRequest {
    private Long classroomId;
    private Long courseId;
    private Integer topicNumber;
    private Integer levelNumber;

    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    private String correctOption;

    private String detailedSolution;
}
