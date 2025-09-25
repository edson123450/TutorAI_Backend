package com.example.tutorai.Topic.Domain;

import com.example.tutorai.Classroom.Domain.Classroom;
import com.example.tutorai.Course.Domain.Course;
import com.example.tutorai.Level.Domain.Level;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
    private LocalDate start_Date;
    @Column(name="end_date",nullable = false)
    private LocalDate end_Date;

    @OneToMany(mappedBy = "topic",
            cascade = CascadeType.ALL,
            orphanRemoval = true, fetch=FetchType.LAZY)
    private Set<Level> levels=new HashSet<>();

    public boolean addLevel(Level level){
        if(levels.add(level)){
            level.setTopic(this);
            return true;
        }
        return false;
    }
    public boolean removeLevel(Level level){
        if(levels.remove(level)){
            level.setTopic(null);
            return true;
        }
        return false;
    }
}
