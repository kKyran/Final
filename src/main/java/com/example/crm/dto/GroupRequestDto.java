package com.example.crm.dto;

import lombok.Data;

import java.util.Set;

@Data
public class GroupRequestDto {
    private String name;
    private Long courseId;
    private Long teacherId;
    private Set<Long> studentIds;
}
