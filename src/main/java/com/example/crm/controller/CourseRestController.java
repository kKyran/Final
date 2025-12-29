package com.example.crm.controller;

import com.example.crm.dto.CourseRequestDto;
import com.example.crm.dto.CourseResponseDto;
import com.example.crm.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
@RequiredArgsConstructor
public class CourseRestController {

    private final CourseService courseService;

    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourseResponseDto> getCourseById(@PathVariable Long id) {
        return courseService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CourseResponseDto> createCourse(@RequestBody CourseRequestDto courseRequestDto) {
        CourseResponseDto createdCourse = courseService.create(courseRequestDto);
        return new ResponseEntity<>(createdCourse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourseResponseDto> updateCourse(@PathVariable Long id, @RequestBody CourseRequestDto courseRequestDto) {
        CourseResponseDto updatedCourse = courseService.update(id, courseRequestDto);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable Long id) {
        courseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
