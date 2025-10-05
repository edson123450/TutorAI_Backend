package com.example.tutorai.Topic.Infrastructure;

import com.example.tutorai.Topic.Domain.Topic;
import com.example.tutorai.Topic.Domain.TopicId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TopicRepository extends JpaRepository<Topic, TopicId> {
    @Query("""

            select coalesce(max(t.id.topicNumber),0)
            from Topic t
            where t.id.classroomId= :classroomId 
            and t.id.courseId= :courseId
            """)
    int findMaxTopicNumber(@Param("classroomId") Long classroomId,
                           @Param("courseId") Long courseId);
    boolean existsById(TopicId id);

    List<Topic> findByIdClassroomIdAndIdCourseId(Long classroomId, Long courseId);
    //List<Topic> findByIdClassroomIdAndIdCourseId(Long classroomId, Long courseId);
    Optional<Topic> findByIdClassroomIdAndIdCourseIdAndIdTopicNumber(Long classroomId,Long courseId,
                                                                     Integer topicNumber);

}
