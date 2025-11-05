package com.example.tutorai.User.Domain;

import com.example.tutorai.User.DTOs.UserDTO;
import com.example.tutorai.User.Infrastructure.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true)
    public UserDTO getMyInfo(Long userId){
        var u=userRepository.findById(userId).
                orElseThrow(()->new IllegalArgumentException("Usuario no encontrado."));
        var dto=new UserDTO();
        dto.setName(u.getName());
        dto.setLastNames(u.getLastNames());
        dto.setAge(u.getAge());

        return dto;
    }





}
