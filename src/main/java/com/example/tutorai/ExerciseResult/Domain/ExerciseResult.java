package com.example.tutorai.ExerciseResult.Domain;


import com.example.tutorai.Exercise.Domain.Exercise;
import com.example.tutorai.Student.Domain.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "exercise_results")
public class ExerciseResult {

    @EmbeddedId
    private ExerciseResultId id;

    @MapsId("exerciseId")
    @ManyToOne(fetch=FetchType.LAZY,optional=false)
    @JoinColumns({
            @JoinColumn(name = "classroom_id",referencedColumnName = "classroom_id"),
            @JoinColumn(name="course_id",referencedColumnName = "course_id"),
            @JoinColumn(name="topic_number",referencedColumnName = "topic_number"),
            @JoinColumn(name="level_number",referencedColumnName = "level_number"),
            @JoinColumn(name="exercise_number",referencedColumnName = "exercise_number")
    })
    private Exercise exercise;

    @MapsId("studentId")
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name="correct_first_try",nullable = false)
    private Boolean correctFirstTry;
    @Column(name="final_passed",nullable = false)
    private Boolean finalPassed;


    public void setExercise(Exercise exercise){
        this.exercise=exercise;
        if(exercise!=null){
            if(this.id==null) this.id=new ExerciseResultId();
            this.id.setExerciseId(exercise.getId());
        }
    }

    public void setStudent(Student student){
        this.student=student;
        if(student!=null){
            if(this.id==null) this.id=new ExerciseResultId();
            this.id.setStudentId(student.getId());
        }
    }

    @Transient
    public Integer getRunNumber(){return (id!=null) ? id.getRunNumber() : null;}


}
