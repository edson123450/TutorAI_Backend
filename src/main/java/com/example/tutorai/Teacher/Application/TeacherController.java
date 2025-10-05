package com.example.tutorai.Teacher.Application;

import com.example.tutorai.Classroom.DTOs.ClassroomCreateRequest;
import com.example.tutorai.Classroom.DTOs.ClassroomDTO;
import com.example.tutorai.Course.DTOs.CourseCreateRequest;
import com.example.tutorai.Course.DTOs.CourseDTO;
import com.example.tutorai.Teacher.Domain.TeacherService;
import com.example.tutorai.Topic.DTOs.TopicCreateRequest;
import com.example.tutorai.Topic.DTOs.TopicDTO;
import com.example.tutorai.User.Domain.Role;
import com.example.tutorai.User.Domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teacher")
@RequiredArgsConstructor
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping("/create/classroom")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('TEACHER')")
    public ClassroomDTO createClassroom(@RequestBody ClassroomCreateRequest request,
                                        Authentication auth){
        Long teacherId=((User) auth.getPrincipal()).getId();
        return teacherService.createClassroom(teacherId,request);
    }

    @PostMapping("/create/classroom/{classroomId}/course")
    //@ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<CourseDTO> createCourse(@PathVariable Long classroomId,
                                                  @RequestBody CourseCreateRequest req,
                                                  Authentication auth){
        Long teacherId=((User) auth.getPrincipal()).getId();
        CourseDTO courseDTO=teacherService.createCourseInClassroom(teacherId,classroomId,req);
        return ResponseEntity.status(HttpStatus.CREATED).body(courseDTO);
    }

    @PostMapping("/create/classroom/{classroomId}/course/{courseId}/topic")
    //@ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<TopicDTO> createTopic(@PathVariable Long classroomId,
                                                @PathVariable Long courseId,
                                                @RequestBody TopicCreateRequest req,
                                                Authentication auth){
        Long teacherId= ((User) auth.getPrincipal()).getId();
        TopicDTO dto=teacherService.createTopicInClassroomCourse(teacherId,classroomId, courseId,req);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    /*@GetMapping("/classrooms")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<List<ClassroomDTO>> getMyClassrooms(@AuthenticationPrincipal User me){
        if(me==null || me.getRole()!= Role.TEACHER){
            throw new AccessDeniedException("Solo profesores pueden listar sus classrooms.");
        }

        List<ClassroomDTO> classrooms=teacherService.getTeacherClassrooms(me.getId());
        return ResponseEntity.ok(classrooms);
    }*/

}
