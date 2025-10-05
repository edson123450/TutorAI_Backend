package com.example.tutorai.Classroom.DTOs;

import lombok.Getter;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
public class ClassroomCreateRequest {
    private String name;

    @NotNull
    private Integer capacity;
}
