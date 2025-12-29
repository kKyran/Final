package com.example.crm.dto;

import lombok.Data;

@Data
public class ApplicationRequestResponseDto {
    private Long id;
    private String userName;
    private String commentary;
    private String phone;
    private boolean handled;
    private CourseResponseDto course;
    private OperatorResponseDto operator;
}
