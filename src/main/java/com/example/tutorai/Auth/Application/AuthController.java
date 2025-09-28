package com.example.tutorai.Auth.Application;


import com.example.tutorai.Auth.DTOs.JwtAuthResponse;
import com.example.tutorai.Auth.DTOs.StudentLoginReq;
import com.example.tutorai.Auth.DTOs.TeacherLoginReq;
import com.example.tutorai.Auth.Domain.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login/teacher")
    public ResponseEntity<JwtAuthResponse> loginTeacher(@RequestBody TeacherLoginReq req){
        return ResponseEntity.ok(authService.loginTeacher(req));
    }

    @PostMapping("/login/student")
    public ResponseEntity<JwtAuthResponse> loginStudent(@RequestBody StudentLoginReq req){
        return ResponseEntity.ok(authService.loginStudent(req));
    }

}
