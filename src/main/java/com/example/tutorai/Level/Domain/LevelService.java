package com.example.tutorai.Level.Domain;

import com.example.tutorai.Level.DTOs.LevelCreateRequest;
import com.example.tutorai.Level.DTOs.LevelDTO;
import com.example.tutorai.Level.Infrastructure.LevelRepository;
import com.example.tutorai.Topic.Domain.Topic;
import com.example.tutorai.Topic.Domain.TopicId;
import com.example.tutorai.Topic.Infrastructure.TopicRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LevelService {

    private final LevelRepository levelRepository;
    private final TopicRepository topicRepository;

    @Transactional
    public LevelDTO createLevel(LevelCreateRequest req){
        TopicId tid=new TopicId();
        tid.setClassroomId(req.getClassroomId());
        tid.setCourseId(req.getCourseId());
        tid.setTopicNumber(req.getTopicNumber());

        Topic topic=topicRepository.findById(tid).
                orElseThrow(()->new IllegalArgumentException("Topic no encontrado"));
        int nextLevelNumber=levelRepository.findTopByTopicOrderByIdLevelNumberDesc(topic).
                map(l -> l.getId().getLevelNumber()+1).
                orElse(1);
        LevelId lid=new LevelId();
        lid.setTopicId(tid);
        lid.setLevelNumber(nextLevelNumber);

        Level level=new Level();
        level.setId(lid);
        level.setTopic(topic);
        level.setName(req.getName());
        level.setMinCorrectFirstTry(req.getMinCorrectFirstTry() != null ? req.getMinCorrectFirstTry(): 1);

        topic.getLevels().add(level);
        topicRepository.save(topic);
        levelRepository.save(level);

        return new LevelDTO(nextLevelNumber,level.getName());
    }

    @Transactional(readOnly = true)
    public List<LevelDTO> getAllByTopic(Long classroomId, Long courseId, Integer topicNumber){
        var list=levelRepository.findAllByTopic(classroomId,courseId,topicNumber);

        return list.stream().map(e->{
            var dto=new LevelDTO();
            dto.setLevelNumber(e.getLevelNumber());
            dto.setName(e.getName());
            return dto;

        }).toList();
    }
}
