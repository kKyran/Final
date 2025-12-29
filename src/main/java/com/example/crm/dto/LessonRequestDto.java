package com.example.crm.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonRequestDto {
    private String topic;
    private LocalDateTime dateTime;
    private Long groupId;
}
