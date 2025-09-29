package com.example.tutorai.Excercise.Domain;

import com.example.tutorai.Level.Domain.LevelId;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class ExerciseId implements Serializable {
    @Embedded
    private LevelId levelId;
    @Column(name = "exercise_number")
    private Integer exerciseNumber;

    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof ExerciseId)) return false;
        ExerciseId that=(ExerciseId) o;
        return Objects.equals(levelId,that.levelId)
                && Objects.equals(exerciseNumber,that.exerciseNumber);
    }

    @Override public int hashCode(){
        return Objects.hash(levelId,exerciseNumber);
    }


}
