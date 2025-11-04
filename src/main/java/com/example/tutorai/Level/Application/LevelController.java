package com.example.tutorai.Level.Application;


import com.example.tutorai.Level.DTOs.LevelCreateRequest;
import com.example.tutorai.Level.DTOs.LevelDTO;
import com.example.tutorai.Level.Domain.LevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/level")
@RequiredArgsConstructor
public class LevelController {
    private final LevelService levelService;

    @PostMapping("/create")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<LevelDTO> createLevel(@RequestBody LevelCreateRequest req) {
        LevelDTO dto = levelService.createLevel(req);
        return ResponseEntity.ok(dto);
    }









}
