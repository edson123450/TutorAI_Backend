package com.example.tutorai.Classroom.Domain;

import com.example.tutorai.Classroom.DTOs.ClassroomDTO;
import com.example.tutorai.Classroom.Infrastructure.ClassroomRepository;
import com.example.tutorai.Course.DTOs.CourseDTO;
import com.example.tutorai.Course.Infrastructure.CourseRepository;
import com.example.tutorai.Exceptions.NotFoundException;
import com.example.tutorai.Student.Domain.Student;
import com.example.tutorai.Student.Infrastructure.StudentRepository;
import com.example.tutorai.Teacher.Domain.Teacher;
import com.example.tutorai.Teacher.Infrastructure.TeacherRepository;
import com.example.tutorai.Topic.DTOs.TopicDTO;
import com.example.tutorai.Topic.DTOs.TopicIdDTO;
import com.example.tutorai.Topic.Domain.Topic;
import com.example.tutorai.Topic.Infrastructure.TopicRepository;
import com.example.tutorai.User.Domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassroomService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final CourseRepository courseRepository;
    private final ClassroomRepository classroomRepository;
    private final TopicRepository topicRepository;

    @Transactional(readOnly = true)
    public List<ClassroomDTO> getClassroomsForUser(Long userId, Role role){
        if(role==Role.STUDENT){
            Student student=studentRepository.findById(userId).
                    orElseThrow(()->new NotFoundException("Student not found"));
            return map(student.getClassrooms().stream().toList());
        } else if (role==Role.TEACHER) {
            Teacher teacher=teacherRepository.findById(userId).
                    orElseThrow(()->new NotFoundException("Teacher not found"));
            return map(teacher.getClassrooms().stream().toList());
        }
        throw new NotFoundException("Unsupoorted role for classroom listing");
    }


    private List<ClassroomDTO> map(List<Classroom> cls){
        return cls.stream().map(c -> new ClassroomDTO(
                c.getId(),
                c.getName(),
                c.getCapacity(),
                c.getActualNumberStudents()
        )).
                collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<CourseDTO> listCoursesForClassroom(Long userId, Role role,Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId).
                orElseThrow(() -> new NotFoundException("Classroom not found"));
        if (role == Role.TEACHER) {
            if (!classroom.getTeacher().getId().equals(userId)) {
                throw new AccessDeniedException("Teacher is not the owner of this classroom");
            }
        } else if (role == Role.STUDENT) {
            boolean belongs = classroom.getStudents().stream().
                    anyMatch(s -> s.getId().equals(userId));
            if (!belongs) {
                throw new AccessDeniedException("Student is not enrolled in this classroom");
            }
        } else {
            throw new AccessDeniedException("Unsupported role");
        }
        return classroom.getCourses().stream().
                map(c -> new CourseDTO(c.getId(), c.getName())).collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public List<TopicDTO> listTopicsForClassroomCourse(
            Long userId, Role role, Long classroomId, Long courseId){
        Classroom classroom=classroomRepository.findById(classroomId).
                orElseThrow(()->new NotFoundException("Classroom not found"));
        authorize(userId,role,classroom);
        boolean courseInClassroom=classroom.getCourses().stream().
                anyMatch(c->c.getId().equals(courseId));
        if(!courseInClassroom) throw new IllegalStateException("Course not assigned to classroom");

        return topicRepository.findByIdClassroomIdAndIdCourseId(classroomId,courseId).
                stream().
                map(this::toDTO).
                collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public TopicDTO getTopicForClassroomCourse(
            Long userId, Role role, Long classroomsId, Long courseId, Integer topicNumber){
        Classroom classroom=classroomRepository.findById(classroomsId).
                orElseThrow(()->new NotFoundException("Classroom not found"));
        authorize(userId,role,classroom);

        Topic topic=topicRepository.findByIdClassroomIdAndIdCourseIdAndIdTopicNumber(
                classroomsId,courseId,topicNumber).orElseThrow(()->new NotFoundException("Topic not found"));

        return toDTO(topic);
    }


    private void authorize(Long userId, Role role, Classroom classroom){
        switch (role){
            case TEACHER -> {
                if(!classroom.getTeacher().getId().equals(userId)){
                    throw new AccessDeniedException("You are not the owner of this classroom");
                }
            }
            case STUDENT -> {
                boolean inCLassroom=classroom.getStudents().stream().
                        anyMatch(s->s.getId().equals(userId));
                if(!inCLassroom) throw new AccessDeniedException("Student not enrolled in this classroom");
            }
            default -> throw new AccessDeniedException("Unauthorized role");
        }

    }

    private TopicDTO toDTO(Topic topic){
        return TopicDTO.builder().
                id(new TopicIdDTO(
                        topic.getId().getClassroomId(),
                        topic.getId().getCourseId(),
                        topic.getId().getTopicNumber())).
                name(topic.getName()).
                build();
    }
}
