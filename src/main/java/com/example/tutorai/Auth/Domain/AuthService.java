package com.example.tutorai.Auth.Domain;


import com.example.tutorai.Auth.DTOs.JwtAuthResponse;
import com.example.tutorai.Auth.DTOs.TeacherLoginReq;
import com.example.tutorai.Student.Infrastructure.StudentRepository;
import com.example.tutorai.Teacher.Domain.Teacher;
import com.example.tutorai.Teacher.Infrastructure.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;
    //private final JwtService jwtService;

    public JwtAuthResponse loginTeacher(TeacherLoginReq req){
        Teacher teacher=teacherRepository.findByEmail(req.getEmail()).
                orElseThrow(() -> new UsernameNotFoundException("Email not registered"));
        if(!passwordEncoder.matches(req.getPassword(),teacher.getPassword())){
            throw new IllegalArgumentException("Invalid credentials");
        }
        String token=jwtService.generateToken
    }






}
