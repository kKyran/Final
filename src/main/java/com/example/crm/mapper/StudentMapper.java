package com.example.crm.mapper;

import com.example.crm.dto.StudentRequestDto;
import com.example.crm.dto.StudentResponseDto;
import com.example.crm.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    StudentResponseDto toResponseDto(Student student);
    List<StudentResponseDto> toResponseDtoList(List<Student> students);

    @Mapping(target = "id", ignore = true)
    Student toEntity(StudentRequestDto studentRequestDto);
}
