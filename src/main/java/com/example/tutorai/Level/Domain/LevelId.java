package com.example.tutorai.Level.Domain;

import com.example.tutorai.Topic.Domain.TopicId;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class LevelId implements Serializable {
    @Embedded
    private TopicId topicId;
    private Integer levelNumber;
    @Override public boolean equals(Object o){
        if(this==o) return true;
        if(!(o instanceof LevelId)) return false;
        LevelId that= (LevelId) o;
        return Objects.equals(topicId,that.topicId) &&
                Objects.equals(levelNumber,that.levelNumber);
    }

    @Override public int hashCode(){return Objects.hash(topicId,levelNumber);}
}
