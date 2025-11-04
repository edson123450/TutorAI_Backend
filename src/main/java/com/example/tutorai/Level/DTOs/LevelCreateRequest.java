package com.example.tutorai.Level.DTOs;

import lombok.Data;

@Data
public class LevelCreateRequest {
    private Long classroomId;
    private Long courseId;
    private Integer topicNumber;
    private String name;
    private Integer minCorrectFirstTry;
}
