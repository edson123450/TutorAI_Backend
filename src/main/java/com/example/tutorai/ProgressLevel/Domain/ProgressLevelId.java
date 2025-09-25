package com.example.tutorai.ProgressLevel.Domain;

import com.example.tutorai.Level.Domain.LevelId;
import com.example.tutorai.Student.Domain.Student;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;


@Getter
@Setter
@Embeddable
public class ProgressLevelId implements Serializable {

    @Embedded
    private LevelId levelId;

    private Long studentId;

    private Integer runNumber;

    @Override public boolean equals(Object o){
        if (this==o) return true;
        if(!(o instanceof ProgressLevelId)) return false;
        ProgressLevelId that=(ProgressLevelId) o;
        return Objects.equals(levelId,that.levelId) &&
                Objects.equals(studentId,that.studentId) &&
                Objects.equals(runNumber,that.runNumber);
    }

    @Override public int hashCode(){return Objects.hash(levelId,studentId,runNumber);}



}
