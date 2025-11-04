package com.example.tutorai.Attempt.Infrastructure;

import com.example.tutorai.Attempt.Domain.Attempt;
import com.example.tutorai.Attempt.Domain.AttemptId;
import com.example.tutorai.Exercise.Domain.Exercise;
import com.example.tutorai.Student.Domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AttemptRepository extends JpaRepository<Attempt, AttemptId> {

    @Query("""
           select count(a) from Attempt a 
           where a.exercise=:exercise and a.student=:student
           """)
    int countByExerciseAndStudent(Exercise exercise, Student student);
}
