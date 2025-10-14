package com.example.tutorai.Message.Application;

import com.example.tutorai.Message.DTOs.MessageDTO;
import com.example.tutorai.Message.Domain.MessageService;
import com.example.tutorai.User.Domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    private final MessageService messageService;

    @PostMapping("/classrooms/{classroomId}/courses/{courseId}/topics/{topicNumber}/boot")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageDTO> bootChat(
            @PathVariable Long classroomId,
            @PathVariable Long courseId,
            @PathVariable Integer topicNumber,
            @AuthenticationPrincipal User me){
        MessageDTO dto=messageService.bootChat(classroomId,courseId,topicNumber,me.getId());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/classrooms/{classroomId}/courses/{courseId}/topics/{topicNumber}/chat")
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageDTO> chat(
            @PathVariable Long classroomId,
            @PathVariable Long courseId,
            @PathVariable Integer topicNumber,
            @AuthenticationPrincipal User me,
            @RequestBody String teacherText) {

        var dto = messageService.chat(
                classroomId, courseId, topicNumber, me.getId(), teacherText, /*files*/ null);

        return ResponseEntity.ok(dto);
    }

    @PostMapping(
            value = "/classrooms/{classroomId}/courses/{courseId}/topics/{topicNumber}/chat-upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    @PreAuthorize("hasRole('TEACHER')")
    public ResponseEntity<MessageDTO> chatWithFiles(
            @PathVariable Long classroomId,
            @PathVariable Long courseId,
            @PathVariable Integer topicNumber,
            @AuthenticationPrincipal User me,
            @RequestPart("message") String teacherText,
            @RequestPart(value = "files", required = false) List<MultipartFile> files) {

        var dto = messageService.chat(
                classroomId, courseId, topicNumber, me.getId(), teacherText, files);

        return ResponseEntity.ok(dto);
    }
}
