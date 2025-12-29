package com.example.crm.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentResponseDto {
    private Long id;
    private BigDecimal amount;
    private LocalDate paymentDate;
    private StudentResponseDto student;
    private CourseResponseDto course;
}
