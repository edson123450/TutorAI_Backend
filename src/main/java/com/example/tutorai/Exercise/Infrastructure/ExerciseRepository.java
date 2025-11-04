package com.example.tutorai.Exercise.Infrastructure;

import com.example.tutorai.Exercise.Domain.Exercise;
import com.example.tutorai.Exercise.Domain.ExerciseId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExerciseRepository extends JpaRepository<Exercise, ExerciseId> {


    @Query("""
            select coalesce(max(e.id.exerciseNumber),0)
            from Exercise e
            where e.level.id.topicId.classroomId=:classroomId 
                and e.level.id.topicId.courseId=:courseId 
                and e.level.id.topicId.topicNumber=:topicNumber 
                and e.level.id.levelNumber=:levelNumber
            """)
    int findMaxExerciseNumberInLevel(@Param("classroomId") Long classroomId,
                                     @Param("courseId") Long courseId,
                                     @Param("topicNumber") Integer topicNumber,
                                     @Param("levelNumber") Integer levelNumber);

    @Query("""
            select e 
            from Exercise e 
            where e.level.id.topicId.classroomId=:classroomId 
                and e.level.id.topicId.courseId=:courseId 
                and e.level.id.topicId.topicNumber=:topicNumber 
                and e.level.id.levelNumber=:levelNumber 
                order by e.id.exerciseNumber asc
            """)
    List<Exercise> findAllByLevel(@Param("classroomId") Long classroomId,
                                  @Param("courseId") Long courseId,
                                  @Param("topicNumber") Integer topicNumber,
                                  @Param("levelNumber") Integer levelNumber);

    @Query("""
           select e 
           from Exercise e 
           where e.level.id.topicId.classroomId=:classroomId 
                and e.level.id.topicId.courseId=:courseId 
                and e.level.id.topicId.topicNumber=:topicNumber 
                and e.level.id.levelNumber=:levelNumber 
                and e.id.exerciseNumber = :exerciseNumber
           """)
    Optional<Exercise> findOneByCompositeKey(@Param("classroomId") Long classroomId,
                                             @Param("courseId") Long courseId,
                                             @Param("topicNumber") Integer topicNumber,
                                             @Param("levelNumber") Integer levelNumber,
                                             @Param("exerciseNumber") Integer exerciseNumber);



}
