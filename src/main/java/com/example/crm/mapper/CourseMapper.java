package com.example.crm.mapper;

import com.example.crm.dto.CourseRequestDto;
import com.example.crm.dto.CourseResponseDto;
import com.example.crm.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    CourseResponseDto toResponseDto(Course course);
    List<CourseResponseDto> toResponseDtoList(List<Course> courses);

    @Mapping(target = "id", ignore = true)
    Course toEntity(CourseRequestDto courseRequestDto);
}
