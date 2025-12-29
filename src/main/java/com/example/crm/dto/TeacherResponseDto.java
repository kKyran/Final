package com.example.crm.dto;

import lombok.Data;

@Data
public class TeacherResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String specialization;
}
