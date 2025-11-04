package com.example.tutorai.Topic.Domain;

import com.example.tutorai.Classroom.Domain.Classroom;
import com.example.tutorai.Course.Domain.Course;
import com.example.tutorai.Level.Domain.Level;
import com.example.tutorai.Message.Domain.Message;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="Topics")
public class Topic {

    @EmbeddedId
    private TopicId id;

    @MapsId("courseId")
    @ManyToOne(optional = false)
    @JoinColumn(name="course_id")
    private Course course;

    @MapsId("classroomId")
    @ManyToOne(optional = false)
    @JoinColumn(name="classroom_id")
    private Classroom classroom;

    @Column(name="name",nullable = false)
    private String name;
    @Column(name="start_date",nullable = false)
    private LocalDate startDate;
    @Column(name="end_date",nullable = false)
    private LocalDate endDate;

    @OneToMany(mappedBy = "topic",
            cascade = CascadeType.ALL,
            orphanRemoval = true, fetch=FetchType.LAZY)
    private Set<Level> levels=new HashSet<>();

    @OneToMany(
            mappedBy = "topic",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    @OrderBy("id.messageNumber ASC")
    @JsonIgnore
    private Set<Message> messages=new HashSet<>();

    /*public boolean addLevel(Level level){
        if(levels.add(level)){
            level.setTopic(this);
            return true;
        }
        return false;
    }*/
    public boolean removeLevel(Level level){
        if(levels.remove(level)){
            level.setTopic(null);
            return true;
        }
        return false;
    }

    public void setClassroom(Classroom classroom){
        this.classroom=classroom;
        classroom.getTopics().add(this);
    }

    public void setCourse(Course course){
        this.course=course;
        course.getTopics().add(this);
    }

    public boolean addMessage(Message m){
        if(messages.add(m)){
            m.setTopic(this);
            return true;
        }
        return false;
    }

    public boolean removeMessage(Message m){
        if(messages.remove(m)){
            m.setTopic(null);
            return true;
        }
        return false;
    }
}
