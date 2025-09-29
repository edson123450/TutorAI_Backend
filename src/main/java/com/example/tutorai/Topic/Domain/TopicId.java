package com.example.tutorai.Topic.Domain;

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
public class TopicId implements Serializable {
    @Column(name="classroom_id")
    private Long classroomId;
    @Column(name="course_id")
    private Long courseId;
    @Column(name = "topic_number")
    private Integer topicNumber;

    @Override public boolean equals (Object o){
        if(this==o) return true;
        if(!(o instanceof TopicId)) return false;
        TopicId that= (TopicId) o;
        return Objects.equals(classroomId,that.classroomId) &&
                Objects.equals(courseId,that.courseId) &&
                Objects.equals(topicNumber,that.topicNumber);
    }
    @Override public int hashCode(){
        return Objects.hash(classroomId,courseId,topicNumber);
    }
}
