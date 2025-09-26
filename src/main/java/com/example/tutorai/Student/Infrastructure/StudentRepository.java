package com.example.tutorai.Student.Infrastructure;

import com.example.tutorai.Student.Domain.Student;
import com.example.tutorai.Teacher.Domain.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long> {
    Optional<Student> findByNameAndLastNames(String name,String lastNames);
}

