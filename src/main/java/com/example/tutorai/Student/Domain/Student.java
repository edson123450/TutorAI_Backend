package com.example.tutorai.Student.Domain;

import com.example.tutorai.User.Domain.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class Student extends User {

    @Column(name="special_number",nullable=false)
    private Integer special_number;

}
