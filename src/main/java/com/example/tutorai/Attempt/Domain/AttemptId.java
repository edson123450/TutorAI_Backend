package com.example.tutorai.Attempt.Domain;

import com.example.tutorai.Excercise.Domain.ExerciseId;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class AttemptId implements Serializable {

    @Embedded
    private ExerciseId exerciseId;

    private Long studentId;

    private Integer attemptNumber;


    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof AttemptId)) return false;
        AttemptId that=(AttemptId) o;
        return Objects.equals(exerciseId,that.exerciseId) &&
                Objects.equals(studentId,that.studentId) &&
                Objects.equals(attemptNumber,that.attemptNumber);
    }

    @Override public int hashCode(){return Objects.hash(exerciseId,studentId,attemptNumber);}




}
