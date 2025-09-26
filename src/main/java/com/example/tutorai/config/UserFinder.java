package com.example.tutorai.config;

import com.example.tutorai.User.Infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserFinder{
    private final UserRepository userRepository;

}
