package com.example.crm.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PaymentRequestDto {
    private BigDecimal amount;
    private LocalDate paymentDate;
    private Long studentId;
    private Long courseId;
}
