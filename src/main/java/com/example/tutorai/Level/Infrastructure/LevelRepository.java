package com.example.tutorai.Level.Infrastructure;

import com.example.tutorai.Level.Domain.Level;
import com.example.tutorai.Level.Domain.LevelId;
import com.example.tutorai.Topic.Domain.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, LevelId> {
    long countByTopic(Topic Topic);
    Optional<Level> findTopByTopicOrderByIdLevelNumberDesc(Topic topic);


    @Query("""
           select e 
           from Level e 
           where e.topic.id.classroomId=:classroomId 
                and e.topic.id.courseId=:courseId 
                and e.topic.id.topicNumber=:topicNumber 
                order by e.id.levelNumber asc
           """)
    List<Level> findAllByTopic(@Param("classroomId") Long classroomId,
                               @Param("courseId") Long courseId,
                               @Param("topicNumber") Integer topicNumber);
}
