package com.example.tutorai.Auth.Application;


import com.example.tutorai.Auth.DTOs.*;
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


    @PostMapping("/register/teacher")
    public ResponseEntity<JwtAuthResponse> registerTeacher(@RequestBody TeacherRegisterReq req){
        return ResponseEntity.ok(authService.registerTeacher(req));
    }

    @PostMapping("/register/student")
    public ResponseEntity<JwtAuthResponse> registerStudent(@RequestBody StudentRegisterReq req){
        return ResponseEntity.ok(authService.registerStudent(req));
    }


}
