package com.example.tutorai.ExerciseResult.Infrastructure;

import com.example.tutorai.Exercise.Domain.Exercise;
import com.example.tutorai.ExerciseResult.Domain.ExerciseResult;
import com.example.tutorai.ExerciseResult.Domain.ExerciseResultId;
import com.example.tutorai.Student.Domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExerciseResultRepository extends JpaRepository<ExerciseResult, ExerciseResultId> {


    @Query("""
           select r from ExerciseResult r 
           where r.exercise=:exercise and r.student=:student 
           order by r.id.runNumber desc
           """)
    Optional<ExerciseResult> findLatestByExerciseAndStudent(Exercise exercise, Student student);
}
