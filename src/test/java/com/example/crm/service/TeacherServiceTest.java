package com.example.crm.service;

import com.example.crm.dto.TeacherRequestDto;
import com.example.crm.dto.TeacherResponseDto;
import com.example.crm.entity.Teacher;
import com.example.crm.mapper.TeacherMapper;
import com.example.crm.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private TeacherMapper teacherMapper;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void getAll() {
        when(teacherRepository.findAll()).thenReturn(Collections.singletonList(new Teacher()));
        when(teacherMapper.toResponseDtoList(any())).thenReturn(Collections.singletonList(new TeacherResponseDto()));

        var result = teacherService.getAll();

        assertEquals(1, result.size());
        verify(teacherRepository).findAll();
    }

    @Test
    void create() {
        TeacherRequestDto request = new TeacherRequestDto();
        when(teacherMapper.toEntity(request)).thenReturn(new Teacher());
        when(teacherRepository.save(any(Teacher.class))).thenAnswer(i -> i.getArguments()[0]);
        when(teacherMapper.toResponseDto(any(Teacher.class))).thenReturn(new TeacherResponseDto());

        teacherService.create(request);

        verify(teacherRepository).save(any(Teacher.class));
    }

    @Test
    void update() {
        Long teacherId = 1L;
        TeacherRequestDto request = new TeacherRequestDto();
        request.setFirstName("Jane");
        Teacher existingTeacher = new Teacher();
        existingTeacher.setId(teacherId);
        existingTeacher.setFirstName("John");

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(existingTeacher));
        when(teacherRepository.save(any(Teacher.class))).thenAnswer(i -> i.getArguments()[0]);
        when(teacherMapper.toResponseDto(any(Teacher.class))).thenReturn(new TeacherResponseDto());

        teacherService.update(teacherId, request);

        verify(teacherRepository).save(existingTeacher);
        assertEquals("Jane", existingTeacher.getFirstName());
    }

    @Test
    void delete() {
        Long teacherId = 1L;
        doNothing().when(teacherRepository).deleteById(teacherId);

        teacherService.delete(teacherId);

        verify(teacherRepository).deleteById(teacherId);
    }
}
