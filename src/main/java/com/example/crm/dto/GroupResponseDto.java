package com.example.crm.dto;

import lombok.Data;

import java.util.Set;

@Data
public class GroupResponseDto {
    private Long id;
    private String name;
    private CourseResponseDto course;
    private TeacherResponseDto teacher;
    private Set<StudentResponseDto> students;
}
