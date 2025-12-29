package com.example.crm.mapper;

import com.example.crm.dto.TeacherRequestDto;
import com.example.crm.dto.TeacherResponseDto;
import com.example.crm.entity.Teacher;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    TeacherResponseDto toResponseDto(Teacher teacher);
    List<TeacherResponseDto> toResponseDtoList(List<Teacher> teachers);

    @Mapping(target = "id", ignore = true)
    Teacher toEntity(TeacherRequestDto teacherRequestDto);
}
