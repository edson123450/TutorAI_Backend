package com.example.tutorai.Exercise.Domain;

import com.example.tutorai.Exercise.DTOs.*;
import com.example.tutorai.Exercise.Infrastructure.ExerciseRepository;
import com.example.tutorai.Level.Domain.Level;
import com.example.tutorai.Level.Domain.LevelId;
import com.example.tutorai.Level.Infrastructure.LevelRepository;
import com.example.tutorai.Topic.Domain.TopicId;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final LevelRepository levelRepository;

    @Transactional
    public void create(ExerciseCreateRequest req){

        TopicId tid=new TopicId();
        tid.setClassroomId(req.getClassroomId());
        tid.setCourseId(req.getCourseId());
        tid.setTopicNumber(req.getTopicNumber());

        LevelId lid=new LevelId();
        lid.setTopicId(tid);
        lid.setLevelNumber(req.getLevelNumber());

        Level level=levelRepository.findById(lid).
                orElseThrow(()-> new IllegalArgumentException("Level no encontrado"));

        int max=exerciseRepository.
                findMaxExerciseNumberInLevel(req.getClassroomId(),
                        req.getCourseId(),req.getTopicNumber(), req.getTopicNumber());

        int nextNumber=max+1;

        ExerciseId eid=new ExerciseId();
        eid.setLevelId(lid);
        eid.setExerciseNumber(nextNumber);

        Exercise ex= new Exercise();
        ex.setId(eid);
        ex.setLevel(level);
        ex.setQuestion(req.getQuestion());
        ex.setOptionA(req.getOptionA());
        ex.setOptionB(req.getOptionB());
        ex.setOptionC(req.getOptionC());
        ex.setOptionD(req.getOptionD());
        ex.setDetailedSolution(req.getDetailedSolution());
        ex.setCorrectOption(req.getCorrectOption());

        level.getExercises().add(ex);
        levelRepository.save(level);

        exerciseRepository.save(ex);
    }



    @Transactional(readOnly = true)
    public List<ExerciseByLevelDTO> getAllByLevel(GetExercisesByLevelDTO req){
        var list=exerciseRepository.findAllByLevel(req.getClassroomId(),req.getCourseId(),
                req.getTopicNumber(), req.getLevelNumber());

        return list.stream().map(e -> {
            var dto= new ExerciseByLevelDTO();
            dto.setExerciseNumber(e.getExerciseNumber());
            dto.setQuestion(e.getQuestion());
            return dto;
        }).toList();

    }


    public ExerciseDTO getOneForTeacher(Long classroomId,
                                        Long courseId,
                                        Integer topicNumber,
                                        Integer levelNumber,
                                        Integer exerciseNumber){
        var e=exerciseRepository.findOneByCompositeKey(classroomId,
                courseId, topicNumber,levelNumber,exerciseNumber).
                orElseThrow(()-> new IllegalArgumentException("Exercise no encontrado"));

        var dto=new ExerciseDTO();
        dto.setQuestion(e.getQuestion());
        dto.setOptionA(e.getOptionA());
        dto.setOptionB(e.getOptionB());
        dto.setOptionC(e.getOptionC());
        dto.setOptionD(e.getOptionD());

        dto.setCorrectOption(e.getCorrectOption());

        dto.setDetailedSolution(e.getDetailedSolution());

        return dto;
    }

    public List<ExerciseForStudentDTO> getAllByLevelForStudent(Long classroomId,
                                                               Long courseId,
                                                               Integer topicNumber,
                                                               Integer levelNumber){
        var list=exerciseRepository.findAllByLevel(classroomId,courseId,
                topicNumber,levelNumber);

        return list.stream().map(e->{
            var dto=new ExerciseForStudentDTO();
            dto.setExerciseNumber(e.getExerciseNumber());
            dto.setQuestion(e.getQuestion());
            dto.setOptionA(e.getOptionA());
            dto.setOptionB(e.getOptionB());
            dto.setOptionC(e.getOptionC());
            dto.setOptionD(e.getOptionD());
            return dto;
        }).toList();
    }









}
