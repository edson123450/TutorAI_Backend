package com.example.tutorai.Student.Domain;

import com.example.tutorai.Attempt.Domain.Attempt;
import com.example.tutorai.Classroom.Domain.Classroom;
import com.example.tutorai.ExerciseResult.Domain.ExerciseResult;
import com.example.tutorai.ExerciseResult.Domain.ExerciseResultId;
import com.example.tutorai.ProgressLevel.Domain.ProgressLevel;
import com.example.tutorai.User.Domain.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="Students")
public class Student extends User {

    @Column(name="password_number",nullable=false)
    private String passwordNumber;

    @ManyToMany(mappedBy = "students")
    private Set<Classroom> classrooms=new HashSet<>();

    @OneToMany(mappedBy = "student",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Attempt> attempts=new HashSet<>();

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<ProgressLevel> progressLevels=new HashSet<>();

    @OneToMany(mappedBy = "student",cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<ExerciseResult> exerciseResults=new HashSet<>();

    public boolean addAttempt(Attempt attempt){
        if(attempts.add(attempt)){
            attempt.setStudent(this);
            return true;
        }
        return false;
    }

    public boolean removeAttempt(Attempt attempt){
        if(attempts.remove(attempt)){
            attempt.setStudent(null);
            return true;
        }
        return false;
    }


    public boolean addProgress(ProgressLevel pl){
        if(progressLevels.add(pl)){
            pl.setStudent(this);
            return true;
        }
        return false;
    }

    public boolean removeProgress(ProgressLevel pl){
        if(progressLevels.remove(pl)){
            pl.setStudent(null);
            return true;
        }
        return false;
    }

    public boolean addResult(ExerciseResult er){
        if(exerciseResults.add(er)){
            er.setStudent(this);
            return true;
        }
        return false;
    }

    public boolean removeResult(ExerciseResult er){
        if(exerciseResults.remove(er)){
            er.setStudent(null);
            return true;
        }
        return false;
    }

    public void addClassroom(Classroom classroom){
        classrooms.add(classroom);
    }

    public void deleteClassroom(Classroom classroom){
        classrooms.remove(classroom);
    }


}
