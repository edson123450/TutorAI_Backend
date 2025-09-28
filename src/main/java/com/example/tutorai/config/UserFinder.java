package com.example.tutorai.config;

import com.example.tutorai.Exceptions.NotFoundException;
import com.example.tutorai.User.Domain.User;
import com.example.tutorai.User.Infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFinder{
    private final UserRepository userRepository;

    public User findByIdOrThrow(Long id){
        return userRepository.findById(id).
                orElseThrow(() -> new NotFoundException("User not found"));
    }

}
