package com.example.crm.service;

import com.example.crm.dto.PaymentRequestDto;
import com.example.crm.dto.PaymentResponseDto;
import com.example.crm.entity.Course;
import com.example.crm.entity.Payment;
import com.example.crm.entity.Student;
import com.example.crm.mapper.PaymentMapper;
import com.example.crm.repository.CourseRepository;
import com.example.crm.repository.PaymentRepository;
import com.example.crm.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void getAll() {
        when(paymentRepository.findAll()).thenReturn(Collections.singletonList(new Payment()));
        when(paymentMapper.toResponseDtoList(any())).thenReturn(Collections.singletonList(new PaymentResponseDto()));

        var result = paymentService.getAll();

        assertEquals(1, result.size());
        verify(paymentRepository).findAll();
    }

    @Test
    void create() {
        PaymentRequestDto request = new PaymentRequestDto();
        request.setStudentId(1L);
        request.setCourseId(1L);

        when(paymentMapper.toEntity(request)).thenReturn(new Payment());
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student()));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course()));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        when(paymentMapper.toResponseDto(any(Payment.class))).thenReturn(new PaymentResponseDto());

        paymentService.create(request);

        verify(paymentRepository).save(any(Payment.class));
    }
    
    @Test
    void update() {
        Long paymentId = 1L;
        PaymentRequestDto request = new PaymentRequestDto();
        request.setAmount(BigDecimal.valueOf(100.00));
        request.setPaymentDate(LocalDate.now());
        request.setStudentId(1L);
        request.setCourseId(1L);

        Payment existingPayment = new Payment();
        existingPayment.setId(paymentId);

        when(paymentRepository.findById(paymentId)).thenReturn(Optional.of(existingPayment));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(new Student()));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course()));
        when(paymentRepository.save(any(Payment.class))).thenAnswer(i -> i.getArguments()[0]);
        when(paymentMapper.toResponseDto(any(Payment.class))).thenReturn(new PaymentResponseDto());

        paymentService.update(paymentId, request);

        verify(paymentRepository).save(existingPayment);
        assertEquals(BigDecimal.valueOf(100.00), existingPayment.getAmount());
    }

    @Test
    void delete() {
        Long paymentId = 1L;
        doNothing().when(paymentRepository).deleteById(paymentId);
        
        paymentService.delete(paymentId);
        
        verify(paymentRepository).deleteById(paymentId);
    }
}
