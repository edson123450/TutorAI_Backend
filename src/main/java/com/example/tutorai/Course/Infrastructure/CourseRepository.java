package com.example.tutorai.Course.Infrastructure;

import com.example.tutorai.Course.Domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course,Long> {

    Optional<Course> findByNameIgnoreCase(String name);
}
