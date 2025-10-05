package com.example.tutorai.Topic.DTOs;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicDTO {

    private TopicIdDTO id;

    private String name;
}
