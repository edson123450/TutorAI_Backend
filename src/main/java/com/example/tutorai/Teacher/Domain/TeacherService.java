package com.example.tutorai.Teacher.Domain;


import com.example.tutorai.Classroom.DTOs.ClassroomCreateRequest;
import com.example.tutorai.Classroom.DTOs.ClassroomDTO;
import com.example.tutorai.Classroom.Domain.Classroom;
import com.example.tutorai.Classroom.Infrastructure.ClassroomRepository;
import com.example.tutorai.Course.DTOs.CourseCreateRequest;
import com.example.tutorai.Course.DTOs.CourseDTO;
import com.example.tutorai.Course.Domain.Course;
import com.example.tutorai.Course.Infrastructure.CourseRepository;
import com.example.tutorai.Exceptions.IllegalArgumentException;
import com.example.tutorai.Exceptions.NotFoundException;
import com.example.tutorai.Student.Domain.Student;
import com.example.tutorai.Teacher.Infrastructure.TeacherRepository;
import com.example.tutorai.Topic.DTOs.TopicCreateRequest;
import com.example.tutorai.Topic.DTOs.TopicDTO;
import com.example.tutorai.Topic.DTOs.TopicIdDTO;
import com.example.tutorai.Topic.Domain.Topic;
import com.example.tutorai.Topic.Domain.TopicId;
import com.example.tutorai.Topic.Infrastructure.TopicRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;
    private final ClassroomRepository classroomRepository;
    private final CourseRepository courseRepository;
    private final TopicRepository topicRepository;

    @Transactional
    public ClassroomDTO createClassroom(Long teacherId, ClassroomCreateRequest req){
        Teacher teacher=teacherRepository.findById(teacherId).
                orElseThrow(()-> new NotFoundException("Teacher not found"));

        Classroom c=new Classroom();
        c.setName(req.getName());
        c.setCapacity(req.getCapacity());
        c.setTeacher(teacher);


        Classroom saved=classroomRepository.save(c);
        teacherRepository.save(teacher);

        return new ClassroomDTO(
                saved.getId(),
                saved.getName(),
                saved.getCapacity(),
                saved.getActualNumberStudents()
        );
    }

    @Transactional
    public CourseDTO createCourseInClassroom(Long teacherId,
                                             Long classroomId,
                                             CourseCreateRequest req){
        Classroom classroom=classroomRepository.findById(classroomId).
                orElseThrow(()->new NotFoundException("Classroom not found"));

        if(!classroom.getTeacher().getId().equals(teacherId)){
            throw new AccessDeniedException("You are not the owner of this classroom");
        }

        Course course=courseRepository.findByNameIgnoreCase(req.getName()).
                orElseGet(()->{
                    Course c=new Course();
                    c.setName(req.getName());
                    return courseRepository.save(c);
                });

        classroom.addCourse(course);
        classroomRepository.save(classroom);
        courseRepository.save(course);

        return new CourseDTO(course.getId(), course.getName());
    }


    @Transactional
    public TopicDTO createTopicInClassroomCourse(Long teacherId, Long classroomId, Long courseId, TopicCreateRequest req){
        Classroom classroom=classroomRepository.findById(classroomId).
                orElseThrow(()-> new IllegalArgumentException("Classroom not found"));
        if(!classroom.getTeacher().getId().equals(teacherId)){
            throw new SecurityException("Teacher does not own this classroom");
        }
        Course course=courseRepository.findById(courseId).
                orElseThrow(()-> new IllegalArgumentException("Course not found"));

        if(!classroom.getCourses().contains(course)){
            throw new IllegalStateException("Course is not assigned to the classroom");
        }
        LocalDate s=req.getStartDate();
        LocalDate e=req.getEndDate();

        if(s.isAfter(e)){
            throw new IllegalArgumentException("startDate must be <= endDate");
        }

        int topicNumber=(req.getTopicNumber()!= null)
                ? req.getTopicNumber() : topicRepository.findMaxTopicNumber(classroomId,courseId)+1;
        TopicId id=new TopicId();
        id.setClassroomId(classroomId);
        id.setCourseId(courseId);
        id.setTopicNumber(topicNumber);

        if(topicRepository.existsById(id)){
            throw new IllegalStateException("Topic with same id already exists");
        }
        Topic topic=new Topic();
        topic.setId(id);
        topic.setClassroom(classroom);
        topic.setCourse(course);
        topic.setName(req.getName());
        topic.setStartDate(s);
        topic.setEndDate(e);

        Topic saved=topicRepository.save(topic);
        classroomRepository.save(classroom);
        courseRepository.save(course);

        return TopicDTO.builder().
                id(new TopicIdDTO(saved.getId().getClassroomId(),
                        saved.getId().getCourseId(),
                        saved.getId().getTopicNumber())).
                name(saved.getName()).build();
    }


    /*@Transactional(readOnly=true)
    public List<ClassroomDTO> getTeacherClassrooms(Long teacherId){
        Teacher teacher=teacherRepository.findById(teacherId).
                orElseThrow(()->new NotFoundException("Teacher not found"));
        return teacher.getClassrooms().stream().map(
                c -> new ClassroomDTO(
                        c.getId(),
                        c.getName(),
                        c.getCapacity(),
                        c.getActualNumberStudents()
                )).collect(Collectors.toList());
    }*/

}
