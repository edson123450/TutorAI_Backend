package com.example.tutorai.User.Controller;


import com.example.tutorai.User.DTOs.UserDTO;
import com.example.tutorai.User.Domain.User;
import com.example.tutorai.User.Domain.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    @PreAuthorize("hasAnyRole('STUDENT','TEACHER')")
    public ResponseEntity<UserDTO> getMyInfo(@AuthenticationPrincipal User me){
        if(me==null) return ResponseEntity.status(401).build();
        return ResponseEntity.ok(userService.getMyInfo(me.getId()));
    }
}
