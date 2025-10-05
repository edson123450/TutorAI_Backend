package com.example.tutorai.Topic.DTOs;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicIdDTO {
    private Long classroomId;
    private Long courseId;
    private Integer topicNumber;


}
