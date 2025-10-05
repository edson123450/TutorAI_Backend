package com.example.tutorai.Classroom.Infrastructure;


import com.example.tutorai.Classroom.Domain.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassroomRepository extends JpaRepository<Classroom,Long>{

}
