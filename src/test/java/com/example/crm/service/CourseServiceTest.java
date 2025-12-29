package com.example.crm.service;

import com.example.crm.dto.CourseRequestDto;
import com.example.crm.dto.CourseResponseDto;
import com.example.crm.entity.Course;
import com.example.crm.mapper.CourseMapper;
import com.example.crm.repository.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private CourseMapper courseMapper;

    @InjectMocks
    private CourseService courseService;

    @Test
    void getAll() {
        when(courseRepository.findAll()).thenReturn(Collections.singletonList(new Course()));
        when(courseMapper.toResponseDtoList(any())).thenReturn(Collections.singletonList(new CourseResponseDto()));

        var result = courseService.getAll();

        assertEquals(1, result.size());
        verify(courseRepository).findAll();
        verify(courseMapper).toResponseDtoList(any());
    }

    @Test
    void getById_found() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.of(new Course()));
        when(courseMapper.toResponseDto(any(Course.class))).thenReturn(new CourseResponseDto());

        var result = courseService.getById(courseId);

        assertTrue(result.isPresent());
        verify(courseRepository).findById(courseId);
        verify(courseMapper).toResponseDto(any(Course.class));
    }
    
    @Test
    void getById_notFound() {
        Long courseId = 1L;
        when(courseRepository.findById(courseId)).thenReturn(Optional.empty());

        var result = courseService.getById(courseId);

        assertFalse(result.isPresent());
        verify(courseRepository).findById(courseId);
    }

    @Test
    void create() {
        CourseRequestDto request = new CourseRequestDto();
        request.setName("New Course");

        when(courseMapper.toEntity(any(CourseRequestDto.class))).thenReturn(new Course());
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArguments()[0]);
        when(courseMapper.toResponseDto(any(Course.class))).thenReturn(new CourseResponseDto());

        courseService.create(request);

        verify(courseRepository).save(any(Course.class));
    }

    @Test
    void update() {
        Long courseId = 1L;
        CourseRequestDto request = new CourseRequestDto();
        request.setName("Updated Course");

        Course existingCourse = new Course();
        existingCourse.setId(courseId);
        existingCourse.setName("Old Course");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(existingCourse));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArguments()[0]);
        when(courseMapper.toResponseDto(any(Course.class))).thenReturn(new CourseResponseDto());

        courseService.update(courseId, request);

        verify(courseRepository).save(existingCourse);
        assertEquals("Updated Course", existingCourse.getName());
    }

    @Test
    void delete() {
        Long courseId = 1L;
        doNothing().when(courseRepository).deleteById(courseId);

        courseService.delete(courseId);

        verify(courseRepository).deleteById(courseId);
    }
}
