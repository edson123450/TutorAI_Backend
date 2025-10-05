package com.example.tutorai.Student.Domain;

import com.example.tutorai.Classroom.DTOs.ClassroomDTO;
import com.example.tutorai.Classroom.Domain.Classroom;
import com.example.tutorai.Classroom.Infrastructure.ClassroomRepository;
import com.example.tutorai.Exceptions.NotFoundException;
import com.example.tutorai.Student.Infrastructure.StudentRepository;
import org.springframework.transaction.annotation.Transactional;
//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final ClassroomRepository classroomRepository;

    @Transactional
    public void joinClassroom(Long studentId, Long classroomId){
        Classroom classroom=classroomRepository.findById(classroomId).
                orElseThrow(()->new NotFoundException("Classroom not found"));
        Student student=studentRepository.findById(studentId).
                orElseThrow(()->new NotFoundException("Student not found"));
        if (classroom.getStudents().contains(student)){
            return;
        }

        if (classroom.getActualNumberStudents()>=classroom.getCapacity()){
            throw new IllegalStateException("Classroom is full");
        }

        classroom.addStudent(student);
        student.addClassroom(classroom);

        classroomRepository.save(classroom);
        studentRepository.save(student);
    }

    /*@Transactional(readOnly=true)
    public List<ClassroomDTO> getStudentClassrooms(Long studentId){
        Student student=studentRepository.findById(studentId).
                orElseThrow(()->new NotFoundException("Student not found"));
        return student.getClassrooms().stream().map(
                c -> new ClassroomDTO(
                        c.getId(),
                        c.getName(),
                        c.getCapacity(),
                        c.getActualNumberStudents()
                )).collect(Collectors.toList());
    }*/

}
