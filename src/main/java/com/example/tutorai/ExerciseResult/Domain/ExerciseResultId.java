package com.example.tutorai.ExerciseResult.Domain;

import com.example.tutorai.Excercise.Domain.ExerciseId;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;
import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@Embeddable
public class ExerciseResultId implements Serializable {

    @Embedded
    private ExerciseId exerciseId;

    private Long studentId;

    private Integer runNumber;

    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof ExerciseId)) return false;
        ExerciseResultId that=(ExerciseResultId) o;
        return Objects.equals(exerciseId,that.exerciseId) &&
                Objects.equals(studentId,that.studentId) &&
                Objects.equals(runNumber,that.runNumber);
    }

    @Override public int hashCode(){
        return Objects.hash(exerciseId,studentId,runNumber);
    }


}
