package com.example.tutorai.Attempt.Domain;


import com.example.tutorai.Excercise.Domain.Exercise;
import com.example.tutorai.Student.Domain.Student;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="Attempts")
public class Attempt {

    @EmbeddedId
    private AttemptId id;

    @MapsId("exerciseId")
    @ManyToOne(fetch= FetchType.LAZY,optional = false)
    @JoinColumns({
            @JoinColumn(name = "classroom_id", referencedColumnName = "classroomId"),
            @JoinColumn(name="course_id",referencedColumnName = "courseId"),
            @JoinColumn(name = "topic_number", referencedColumnName = "topicNumber"),
            @JoinColumn(name="level_number", referencedColumnName = "levelNumber"),
            @JoinColumn(name="exercise_number", referencedColumnName = "exerciseNumber")
    })
    private Exercise exercise;

    @MapsId("studentId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name="marked_option", nullable = false, length = 1)
    private String markedOption;


    @Transient
    public Integer getAttemptNumber(){return (id!=null) ? id.getAttemptNumber() : null;}

    public void setExercise(Exercise exercise){
        this.exercise=exercise;
        if(exercise!=null){
            if(this.id==null){
                this.id= new AttemptId();
            }
            this.id.setExerciseId(exercise.getId());
        }
    }

    public void setStudent(Student student){
        this.student=student;
        if(student!=null){
            if(this.id==null){
                this.id=new AttemptId();
            }
            this.id.setStudentId(student.getId());
        }
    }




}
