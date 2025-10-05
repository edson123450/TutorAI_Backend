package com.example.tutorai.Topic.DTOs;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class TopicCreateRequest {
    String name;
    LocalDate startDate;
    LocalDate endDate;
    Integer topicNumber;
}
