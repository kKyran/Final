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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final PaymentMapper paymentMapper;

    public List<PaymentResponseDto> getAll() {
        return paymentMapper.toResponseDtoList(paymentRepository.findAll());
    }

    public Optional<PaymentResponseDto> getById(Long id) {
        return paymentRepository.findById(id).map(paymentMapper::toResponseDto);
    }

    @Transactional
    public PaymentResponseDto create(PaymentRequestDto requestDto) {
        Payment payment = paymentMapper.toEntity(requestDto);

        Student student = studentRepository.findById(requestDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        payment.setStudent(student);

        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        payment.setCourse(course);

        Payment savedPayment = paymentRepository.save(payment);
        return paymentMapper.toResponseDto(savedPayment);
    }

    @Transactional
    public PaymentResponseDto update(Long id, PaymentRequestDto requestDto) {
        Payment existingPayment = paymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        existingPayment.setAmount(requestDto.getAmount());
        existingPayment.setPaymentDate(requestDto.getPaymentDate());

        Student student = studentRepository.findById(requestDto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        existingPayment.setStudent(student);

        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        existingPayment.setCourse(course);

        Payment updatedPayment = paymentRepository.save(existingPayment);
        return paymentMapper.toResponseDto(updatedPayment);
    }

    @Transactional
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }
}
