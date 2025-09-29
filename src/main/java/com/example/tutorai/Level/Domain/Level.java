package com.example.tutorai.Level.Domain;

import com.example.tutorai.Excercise.Domain.Exercise;
import com.example.tutorai.ProgressLevel.Domain.ProgressLevel;
import com.example.tutorai.Topic.Domain.Topic;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name="Levels")
public class Level {

    @EmbeddedId
    private LevelId id;

    @MapsId("topicId")
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumns({
            @JoinColumn(name="classroom_id", referencedColumnName="classroom_id"),
            @JoinColumn(name="course_id",referencedColumnName = "course_id"),
            @JoinColumn(name="topic_number", referencedColumnName = "topic_number")
    })
    private Topic topic;

    @Column(name="min_correct_first_try",nullable = false)
    private Integer minCorrectFirstTry;

    @Column(name="label", length=30, nullable = false)
    private String label;

    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Exercise> exercises =new HashSet<>();


    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL,orphanRemoval = true,fetch = FetchType.LAZY)
    private Set<ProgressLevel> progressLevels=new HashSet<>();

    public Level(){}
    public Level(LevelId id, Topic topic, Integer minCorrectFirstTry, String label){
        this.id=id;
        this.topic=topic;
        this.minCorrectFirstTry=minCorrectFirstTry;
        this.label=label;
    }

    @Transient
    public Integer getLevelNumber(){
        return id!=null ? id.getLevelNumber():null;
    }

    public void setTopic(Topic topic){
        this.topic=topic;
        if(topic!=null){
            if(this.id==null) this.id=new LevelId();
            this.id.setTopicId(topic.getId());
        }
    }

    public boolean addExercise(Exercise exercise){
        if (exercise ==null) return false;

        exercise.setLevel(this);
        boolean added=this.exercises.add(exercise);
        return added;
    }

    public boolean removeExercise(Exercise exercise){
        if(exercise ==null) return false;
        boolean removed=this.exercises.remove(exercise);
        if(removed){
            exercise.setLevel(null);
        }
        return removed;
    }


    public boolean addProgress(ProgressLevel pl){
        if(progressLevels.add(pl)){
            pl.setLevel(this);
            return true;
        }
        return false;
    }

    public boolean removeProgress(ProgressLevel pl){
        if(progressLevels.remove(pl)){
            pl.setLevel(null);
            return true;
        }
        return false;
    }




}
