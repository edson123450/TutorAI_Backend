package com.example.tutorai.Attempt.Domain;


import com.example.tutorai.Attempt.Infrastructure.AttemptRepository;
import com.example.tutorai.Exercise.Domain.Exercise;
import com.example.tutorai.Exercise.Infrastructure.ExerciseRepository;
import com.example.tutorai.ExerciseResult.Domain.ExerciseResult;
import com.example.tutorai.ExerciseResult.Domain.ExerciseResultId;
import com.example.tutorai.ExerciseResult.Infrastructure.ExerciseResultRepository;
import com.example.tutorai.Student.Infrastructure.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttemptService {

    private final ExerciseRepository exerciseRepository;
    private final StudentRepository studentRepository;
    private final AttemptRepository attemptRepository;
    private final ExerciseResultRepository exerciseResultRepository;

    @Transactional
    public void submitAnswer(Long studentId, Long classroomId, Long courseId,
                             Integer topicNumber, Integer levelNumber,
                             Integer exerciseNumber, String markedOption){
        Exercise exercise=exerciseRepository.findOneByCompositeKey(classroomId,courseId,
                topicNumber, levelNumber, exerciseNumber).
                orElseThrow(()-> new IllegalArgumentException("Exercise no encontrado"));

        var student=studentRepository.findById(studentId).
                orElseThrow(()-> new IllegalArgumentException("Student no encontrado"));

        Attempt attempt=new Attempt();
        attempt.setMarkedOption(markedOption);

        int attemptCount=attemptRepository.countByExerciseAndStudent(exercise,student);
        var attemptId=new AttemptId();
        attemptId.setExerciseId(exercise.getId());
        attemptId.setStudentId(studentId);
        attemptId.setAttemptNumber(attemptCount+1);

        attempt.setId(attemptId);
        attempt.setExercise(exercise);
        attempt.setStudent(student);

        student.addAttempt(attempt);
        exercise.addAttempt(attempt);
        studentRepository.save(student);
        exerciseRepository.save(exercise);
        attemptRepository.save(attempt);

        boolean isCorrect=exercise.getCorrectOption().equalsIgnoreCase(markedOption);

        ExerciseResult result=exerciseResultRepository.
                findLatestByExerciseAndStudent(exercise,student).
                orElseGet(()->{
                    ExerciseResult r=new ExerciseResult();
                    r.setExercise(exercise);
                    r.setStudent(student);
                    r.setCorrectFirstTry(isCorrect);
                    r.setFinalPassed(isCorrect);
                    var rid=new ExerciseResultId();
                    rid.setExerciseId(exercise.getId());
                    rid.setStudentId(studentId);
                    rid.setRunNumber(1);
                    r.setId(rid);
                    return r;
                });

        // check
        if(!isCorrect) result.setFinalPassed(false);


        student.addResult(result);
        exercise.addResult(result);
        studentRepository.save(student);
        exerciseRepository.save(exercise);
        exerciseResultRepository.save(result);

    }
}
