package com.example.crm.service;

import com.example.crm.dto.StudentRequestDto;
import com.example.crm.dto.StudentResponseDto;
import com.example.crm.entity.Student;
import com.example.crm.mapper.StudentMapper;
import com.example.crm.repository.StudentRepository;
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
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private StudentMapper studentMapper;

    @InjectMocks
    private StudentService studentService;

    @Test
    void getAll() {
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(new Student()));
        when(studentMapper.toResponseDtoList(any())).thenReturn(Collections.singletonList(new StudentResponseDto()));

        var result = studentService.getAll();

        assertEquals(1, result.size());
        verify(studentRepository).findAll();
    }

    @Test
    void create() {
        StudentRequestDto request = new StudentRequestDto();
        when(studentMapper.toEntity(request)).thenReturn(new Student());
        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArguments()[0]);
        when(studentMapper.toResponseDto(any(Student.class))).thenReturn(new StudentResponseDto());

        studentService.create(request);

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    void update() {
        Long studentId = 1L;
        StudentRequestDto request = new StudentRequestDto();
        request.setFirstName("Jane");
        Student existingStudent = new Student();
        existingStudent.setId(studentId);
        existingStudent.setFirstName("John");

        when(studentRepository.findById(studentId)).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArguments()[0]);
        when(studentMapper.toResponseDto(any(Student.class))).thenReturn(new StudentResponseDto());

        studentService.update(studentId, request);

        verify(studentRepository).save(existingStudent);
        assertEquals("Jane", existingStudent.getFirstName());
    }

    @Test
    void delete() {
        Long studentId = 1L;
        doNothing().when(studentRepository).deleteById(studentId);

        studentService.delete(studentId);

        verify(studentRepository).deleteById(studentId);
    }
}
