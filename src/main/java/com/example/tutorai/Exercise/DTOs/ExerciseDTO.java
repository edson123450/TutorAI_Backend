package com.example.tutorai.Exercise.DTOs;

import lombok.Data;

@Data
public class ExerciseDTO {

    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;

    private String correctOption;

    private String detailedSolution;
}
