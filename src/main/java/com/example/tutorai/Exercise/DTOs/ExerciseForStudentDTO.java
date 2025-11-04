package com.example.tutorai.Exercise.DTOs;

import lombok.Data;

@Data
public class ExerciseForStudentDTO {
    private Integer exerciseNumber;
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
}
