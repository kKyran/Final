package com.example.crm.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonResponseDto {
    private Long id;
    private String topic;
    private LocalDateTime dateTime;
    private GroupResponseDto group;
}
