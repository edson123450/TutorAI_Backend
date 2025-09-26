package com.example.tutorai.Teacher.Infrastructure;

import com.example.tutorai.Teacher.Domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TeacherRepository extends JpaRepository<Teacher,Long> {
    Optional<Teacher> findByEmail(String email);
}
