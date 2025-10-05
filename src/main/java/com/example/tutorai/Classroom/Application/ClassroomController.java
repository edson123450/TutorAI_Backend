package com.example.tutorai.Classroom.Application;

import com.example.tutorai.Classroom.DTOs.ClassroomDTO;
import com.example.tutorai.Classroom.Domain.ClassroomService;
import com.example.tutorai.Course.DTOs.CourseDTO;
import com.example.tutorai.Topic.DTOs.TopicDTO;
import com.example.tutorai.Topic.DTOs.TopicIdDTO;
import com.example.tutorai.User.Domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/classrooms")
@RequiredArgsConstructor
public class ClassroomController {


    private final ClassroomService classroomService;

    @GetMapping
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER')")
    public ResponseEntity<List<ClassroomDTO>> getMyClassrooms(@AuthenticationPrincipal User me){
        List<ClassroomDTO> list=classroomService.getClassroomsForUser(me.getId(),me.getRole());
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{classroomId}/courses")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER')")
    public ResponseEntity<List<CourseDTO>> listCourses(@PathVariable Long classroomId,
                                                       @AuthenticationPrincipal User me){
        List<CourseDTO> courses=
                classroomService.listCoursesForClassroom(me.getId(), me.getRole(),classroomId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{classroomId}/courses/{courseId}/topics")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER')")
    public ResponseEntity<List<TopicDTO>> listTopics(
            @PathVariable Long classroomId,
            @PathVariable Long courseId,
            @AuthenticationPrincipal User me){
        List<TopicDTO> topics=classroomService.listTopicsForClassroomCourse(
                me.getId(),me.getRole(),classroomId,courseId);
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{classroomId}/courses/{courseId}/topics/{topicNumber}")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER')")
    public ResponseEntity<TopicDTO> getTopic(
            @PathVariable Long classroomId,
            @PathVariable Long courseId,
            @PathVariable Integer topicNumber,
            @AuthenticationPrincipal User me){
        TopicDTO dto=classroomService.getTopicForClassroomCourse(me.getId(),
                me.getRole(), classroomId, courseId, topicNumber);
        return ResponseEntity.ok(dto);
    }
}


