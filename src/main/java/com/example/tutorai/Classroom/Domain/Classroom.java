package com.example.tutorai.Classroom.Domain;

import com.example.tutorai.Course.Domain.Course;
import com.example.tutorai.Student.Domain.Student;
import com.example.tutorai.Teacher.Domain.Teacher;
import com.example.tutorai.Topic.Domain.Topic;
import com.example.tutorai.Util.ClassroomCodeGenerator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="Classrooms")
public class Classroom {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    //@Id
    //@Column(name="id", nullable = false, unique = true, length=32)
    //private String classroom_code;

    @Column(name="name",nullable=false)
    private String name;

    @ManyToOne
    @JoinColumn(name="teacher_id",nullable = false)
    private Teacher teacher;

    @Column(name="capacity",nullable=false)
    private Integer capacity;

    //@Column(name = "actual_number_students",nullable = false)
    //private Integer actualNumberStudents;

    @ManyToMany
    @JoinTable(name="Classrooms_Students",
            joinColumns=@JoinColumn(name="classroom_id"),
            inverseJoinColumns = @JoinColumn(name="student_id"),
            uniqueConstraints = @UniqueConstraint(columnNames={"classroom_id","student_id"}))
    private Set<Student> students=new HashSet<>();

    @ManyToMany
    @JoinTable(name="Classroom_Courses",
            joinColumns=@JoinColumn(name="classroom_id"),
            inverseJoinColumns=@JoinColumn(name="course_id"),
            uniqueConstraints=@UniqueConstraint(columnNames={"classroom_id","course_id"}))
    private Set<Course> courses=new HashSet<>();

    @OneToMany(mappedBy="classroom", cascade=CascadeType.ALL,orphanRemoval = true)
    private Set<Topic> topics=new HashSet<>();


    /*@PrePersist
    private void ensureCode(){
        if(this.id==null || this.id.isBlank()){
            this.id= ClassroomCodeGenerator.generate();
        }
    }*/


    public void setTeacher(Teacher teacher){
        this.teacher=teacher;
        if(teacher!=null && !teacher.getClassrooms().contains(this)){
            teacher.getClassrooms().add(this);
        }
    }

    public boolean addStudent(Student student){
        if(students.add(student)){
            student.getClassrooms().add(this);
            return true;
        }
        return false;
    }

    public boolean removeStudent(Student student){
        if(students.remove(student)){
            student.getClassrooms().remove(this);
            return true;
        }
        return false;
    }


    public boolean addCourse(Course course){
        if(courses.add(course)){
            course.getClassrooms().add(this);
            return true;
        }
        return false;
    }

    public boolean removeCourse(Course course){
        if(courses.remove(course)){
            course.getClassrooms().remove(this);
            return true;
        }
        return false;
    }

    @Transient
    public Integer getActualNumberStudents(){
        return students!=null ? students.size() : 0;
    }


}
