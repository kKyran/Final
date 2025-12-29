package com.example.crm.service;

import com.example.crm.dto.TeacherRequestDto;
import com.example.crm.dto.TeacherResponseDto;
import com.example.crm.entity.Teacher;
import com.example.crm.mapper.TeacherMapper;
import com.example.crm.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TeacherService {
    private final TeacherRepository teacherRepository;
    private final TeacherMapper teacherMapper;

    public List<TeacherResponseDto> getAll() {
        return teacherMapper.toResponseDtoList(teacherRepository.findAll());
    }

    public Optional<TeacherResponseDto> getById(Long id) {
        return teacherRepository.findById(id).map(teacherMapper::toResponseDto);
    }

    @Transactional
    public TeacherResponseDto create(TeacherRequestDto teacherRequestDto) {
        Teacher teacher = teacherMapper.toEntity(teacherRequestDto);
        Teacher savedTeacher = teacherRepository.save(teacher);
        return teacherMapper.toResponseDto(savedTeacher);
    }

    @Transactional
    public TeacherResponseDto update(Long id, TeacherRequestDto teacherRequestDto) {
        Teacher existingTeacher = teacherRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        existingTeacher.setFirstName(teacherRequestDto.getFirstName());
        existingTeacher.setLastName(teacherRequestDto.getLastName());
        existingTeacher.setEmail(teacherRequestDto.getEmail());
        existingTeacher.setSpecialization(teacherRequestDto.getSpecialization());
        Teacher updatedTeacher = teacherRepository.save(existingTeacher);
        return teacherMapper.toResponseDto(updatedTeacher);
    }

    @Transactional
    public void delete(Long id) {
        teacherRepository.deleteById(id);
    }
}
