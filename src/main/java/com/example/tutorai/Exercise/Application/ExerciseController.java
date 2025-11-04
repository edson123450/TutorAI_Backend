package com.example.tutorai.Exercise.Application;


import com.example.tutorai.Exercise.DTOs.ExerciseByLevelDTO;
import com.example.tutorai.Exercise.DTOs.ExerciseCreateRequest;
import com.example.tutorai.Exercise.DTOs.GetExercisesByLevelDTO;
import com.example.tutorai.Exercise.Domain.ExerciseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/exercise")
@RequiredArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<Void> crate(@RequestBody ExerciseCreateRequest req){
        exerciseService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).build();

    }

    @PostMapping("/bylevel/getall")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<ExerciseByLevelDTO>> getAllByLevel(@RequestBody  GetExercisesByLevelDTO req){
        var result=exerciseService.getAllByLevel(req);
        return ResponseEntity.ok(result);
    }






}
