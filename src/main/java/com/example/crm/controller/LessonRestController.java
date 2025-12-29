package com.example.crm.controller;

import com.example.crm.dto.LessonRequestDto;
import com.example.crm.dto.LessonResponseDto;
import com.example.crm.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonRestController {

    private final LessonService lessonService;

    @GetMapping
    public ResponseEntity<List<LessonResponseDto>> getAllLessons() {
        return ResponseEntity.ok(lessonService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LessonResponseDto> getLessonById(@PathVariable Long id) {
        return lessonService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LessonResponseDto> createLesson(@RequestBody LessonRequestDto lessonRequestDto) {
        LessonResponseDto createdLesson = lessonService.create(lessonRequestDto);
        return new ResponseEntity<>(createdLesson, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LessonResponseDto> updateLesson(@PathVariable Long id, @RequestBody LessonRequestDto lessonRequestDto) {
        LessonResponseDto updatedLesson = lessonService.update(id, lessonRequestDto);
        return ResponseEntity.ok(updatedLesson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLesson(@PathVariable Long id) {
        lessonService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
