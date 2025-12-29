package com.example.crm.controller;

import com.example.crm.dto.TeacherRequestDto;
import com.example.crm.dto.TeacherResponseDto;
import com.example.crm.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
public class TeacherRestController {

    private final TeacherService teacherService;

    @GetMapping
    public ResponseEntity<List<TeacherResponseDto>> getAllTeachers() {
        return ResponseEntity.ok(teacherService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> getTeacherById(@PathVariable Long id) {
        return teacherService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TeacherResponseDto> createTeacher(@RequestBody TeacherRequestDto teacherRequestDto) {
        TeacherResponseDto createdTeacher = teacherService.create(teacherRequestDto);
        return new ResponseEntity<>(createdTeacher, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherResponseDto> updateTeacher(@PathVariable Long id, @RequestBody TeacherRequestDto teacherRequestDto) {
        TeacherResponseDto updatedTeacher = teacherService.update(id, teacherRequestDto);
        return ResponseEntity.ok(updatedTeacher);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeacher(@PathVariable Long id) {
        teacherService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
