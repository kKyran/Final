package com.example.crm.dto;

import lombok.Data;

@Data
public class ApplicationRequestDto {
    private Long id;
    private String userName;
    private String commentary;
    private String phone;
    private boolean handled;
    private CourseDto course;
    private OperatorDto operator;
}
