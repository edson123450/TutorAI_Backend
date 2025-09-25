package com.example.tutorai.ProgressLevel.Domain;


import com.example.tutorai.Level.Domain.Level;
import com.example.tutorai.Student.Domain.Student;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "progress_levels")
public class ProgressLevel {

    @EmbeddedId
    private ProgressLevelId id;

    @MapsId("levelId")
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumns({
            @JoinColumn(name="classroom_id",referencedColumnName = "classroomId"),
            @JoinColumn(name = "course_id", referencedColumnName = "courseId"),
            @JoinColumn(name="topic_number",referencedColumnName = "topicNumber"),
            @JoinColumn(name="level_number",referencedColumnName = "levelNumber")
    })
    private Level level;

    @MapsId("studentId")
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name="approved",nullable = false)
    private Boolean approved;

    @Column(name = "correct_first_try",nullable = false)
    private Integer correctFirstTry;

    @Column(name = "end_date",nullable = false)
    private LocalDate endDate;


    public void setLevel(Level level){
        this.level=level;
        if(level!=null){
            if(this.id==null){
                this.id=new ProgressLevelId();
            }
            this.id.setLevelId(level.getId());
        }
    }

    public void setStudent(Student student){
        this.student=student;
        if(student!=null){
            if(this.id==null) this.id=new ProgressLevelId();
            this.id.setStudentId(student.getId());
        }

    }

    @Transient
    public Integer getRunNumber(){return (id!=null) ? id.getRunNumber() : null;}







}
