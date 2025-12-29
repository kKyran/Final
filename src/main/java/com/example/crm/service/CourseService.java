package com.example.crm.service;

import com.example.crm.dto.CourseRequestDto;
import com.example.crm.dto.CourseResponseDto;
import com.example.crm.entity.Course;
import com.example.crm.mapper.CourseMapper;
import com.example.crm.repository.CourseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CourseService {
    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;

    public List<CourseResponseDto> getAll() {
        return courseMapper.toResponseDtoList(courseRepository.findAll());
    }

    public Optional<CourseResponseDto> getById(Long id) {
        return courseRepository.findById(id).map(courseMapper::toResponseDto);
    }

    @Transactional
    public CourseResponseDto create(CourseRequestDto courseRequestDto) {
        Course course = courseMapper.toEntity(courseRequestDto);
        Course savedCourse = courseRepository.save(course);
        return courseMapper.toResponseDto(savedCourse);
    }

    @Transactional
    public CourseResponseDto update(Long id, CourseRequestDto courseRequestDto) {
        Course existingCourse = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found")); // Replace with a proper exception
        existingCourse.setName(courseRequestDto.getName());
        Course updatedCourse = courseRepository.save(existingCourse);
        return courseMapper.toResponseDto(updatedCourse);
    }

    @Transactional
    public void delete(Long id) {
        courseRepository.deleteById(id);
    }
}
