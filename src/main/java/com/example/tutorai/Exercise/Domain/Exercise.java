package com.example.tutorai.Exercise.Domain;


import com.example.tutorai.Attempt.Domain.Attempt;
import com.example.tutorai.ExerciseResult.Domain.ExerciseResult;
import com.example.tutorai.Level.Domain.Level;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="Exercises")
public class Exercise {

    @EmbeddedId
    private ExerciseId id;

    @MapsId("levelId")
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumns({
            @JoinColumn(name="classroom_id",referencedColumnName="classroom_id"),
            @JoinColumn(name="course_id",referencedColumnName = "course_id"),
            @JoinColumn(name = "topic_number",referencedColumnName = "topic_number"),
            @JoinColumn(name = "level_number",referencedColumnName = "level_number")
    })
    private Level level;

    @OneToMany(mappedBy = "exercise",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    private Set<Attempt> attempts=new HashSet<>();



    @OneToMany(mappedBy = "exercise",cascade = CascadeType.ALL,orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<ExerciseResult> exerciseResults=new HashSet<>();

    @Column(name = "question",nullable = false, length = 500)
    private String question;
    @Column(name = "option_a",nullable = false,length = 255)
    private String optionA;
    @Column(name="option_b",nullable = false,length = 255)
    private String optionB;
    @Column(name="option_c",nullable = false,length = 255)
    private String optionC;
    @Column(name = "option_d",nullable = false,length = 255)
    private String optionD;
    @Column(name = "correct_option",nullable = false,length = 1)
    private String correctOption;
    @Column(name = "detailed_solution",nullable = false)
    private String detailedSolution;

    /*@Column(name = "label",length = 30)
    private String label;*/


    public void setLevel(Level level){
        this.level=level;
        if(level!=null){
            if(this.id==null){
                this.id=new ExerciseId();
            }
            this.id.setLevelId(level.getId());
        }
    }

    @Transient
    public Integer getExerciseNumber(){
        return (id!=null) ? id.getExerciseNumber() : null;
    }

    public boolean addAttempt(Attempt attempt){
        if(attempts.add(attempt)){
            attempt.setExercise(this);
            return true;
        }
        return false;
    }

    public boolean removeAttempt(Attempt attempt){
        if(attempts.remove(attempt)){
            attempt.setExercise(null);
            return true;
        }
        return false;
    }

    public boolean addResult(ExerciseResult er){
        if(exerciseResults.add(er)){
            er.setExercise(this);
            return true;
        }
        return false;
    }

    public boolean removeResult(ExerciseResult er){
        if(exerciseResults.remove(er)){
            er.setExercise(null);
            return true;
        }
        return false;
    }



}
