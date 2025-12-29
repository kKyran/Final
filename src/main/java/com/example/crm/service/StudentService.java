package com.example.crm.service;

import com.example.crm.dto.StudentRequestDto;
import com.example.crm.dto.StudentResponseDto;
import com.example.crm.entity.Student;
import com.example.crm.mapper.StudentMapper;
import com.example.crm.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public List<StudentResponseDto> getAll() {
        return studentMapper.toResponseDtoList(studentRepository.findAll());
    }

    public Optional<StudentResponseDto> getById(Long id) {
        return studentRepository.findById(id).map(studentMapper::toResponseDto);
    }

    @Transactional
    public StudentResponseDto create(StudentRequestDto studentRequestDto) {
        Student student = studentMapper.toEntity(studentRequestDto);
        Student savedStudent = studentRepository.save(student);
        return studentMapper.toResponseDto(savedStudent);
    }

    @Transactional
    public StudentResponseDto update(Long id, StudentRequestDto studentRequestDto) {
        Student existingStudent = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        existingStudent.setFirstName(studentRequestDto.getFirstName());
        existingStudent.setLastName(studentRequestDto.getLastName());
        existingStudent.setEmail(studentRequestDto.getEmail());
        existingStudent.setPhone(studentRequestDto.getPhone());
        Student updatedStudent = studentRepository.save(existingStudent);
        return studentMapper.toResponseDto(updatedStudent);
    }

    @Transactional
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}
