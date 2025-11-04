package com.example.tutorai.Student.Application;


import com.example.tutorai.Attempt.DTOs.AttemptAnswerRequest;
import com.example.tutorai.Attempt.Domain.AttemptService;
import com.example.tutorai.Classroom.DTOs.ClassroomDTO;
import com.example.tutorai.Classroom.DTOs.JoinClassroomRequest;
import com.example.tutorai.Exercise.DTOs.ExerciseForStudentDTO;
import com.example.tutorai.Exercise.Domain.ExerciseService;
import com.example.tutorai.Student.Domain.StudentService;
import com.example.tutorai.User.Domain.Role;
import com.example.tutorai.User.Domain.User;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/student")
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final ExerciseService exerciseService;
    private final AttemptService attemptService;

    @PostMapping("/join/classroom")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Void> joinClassroom(@AuthenticationPrincipal User authUser,
                                          @RequestBody JoinClassroomRequest req){
        if(authUser.getRole()!= Role.STUDENT){
            return ResponseEntity.status(403).build();
        }

        studentService.joinClassroom(authUser.getId(), req.getClassroomId());
        return ResponseEntity.ok().build();

    }

    /*@GetMapping("/classrooms")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<ClassroomDTO>> getMyClassrooms(@AuthenticationPrincipal User me){
        if(me==null || me.getRole()!=Role.STUDENT){
            throw new AccessDeniedException("Solo estudiantes pueden listar sus classrooms.");
        }

        List<ClassroomDTO> classrooms=studentService.getStudentClassrooms(me.getId());
        return ResponseEntity.ok(classrooms);
    }*/
    @GetMapping("/classroom/{classroomId}/course/{courseId}/topic/{topicNumber}/level/{levelNumber}/exercises")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<ExerciseForStudentDTO>> getExercisesByLevel(@PathVariable Long classroomId,
                                                                           @PathVariable Long courseId,
                                                                           @PathVariable Integer topicNumber,
                                                                           @PathVariable Integer levelNumber){
        var result=exerciseService.getAllByLevelForStudent(classroomId, courseId,topicNumber,levelNumber);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/classroom/{classroomId}/course/{courseId}/topic/{topicNumber}/level/{levelNumber}/exercises/{exerciseNumber}/answer")
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<Void> submitAnswer(@PathVariable Long classroomId,
                                             @PathVariable Long courseId,
                                             @PathVariable Integer topicNumber,
                                             @PathVariable Integer levelNumber,
                                             @PathVariable Integer exerciseNumber,
                                             @AuthenticationPrincipal User me,
                                             @RequestBody AttemptAnswerRequest request){
        attemptService.submitAnswer(me.getId(), classroomId, courseId,
                topicNumber, levelNumber,exerciseNumber,request.getMarkedOption());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }








}
