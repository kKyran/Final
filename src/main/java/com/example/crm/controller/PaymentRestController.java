package com.example.crm.controller;

import com.example.crm.dto.PaymentRequestDto;
import com.example.crm.dto.PaymentResponseDto;
import com.example.crm.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentRestController {

    private final PaymentService paymentService;

    @GetMapping
    public ResponseEntity<List<PaymentResponseDto>> getAllPayments() {
        return ResponseEntity.ok(paymentService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> getPaymentById(@PathVariable Long id) {
        return paymentService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaymentResponseDto> createPayment(@RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto createdPayment = paymentService.create(paymentRequestDto);
        return new ResponseEntity<>(createdPayment, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentResponseDto> updatePayment(@PathVariable Long id, @RequestBody PaymentRequestDto paymentRequestDto) {
        PaymentResponseDto updatedPayment = paymentService.update(id, paymentRequestDto);
        return ResponseEntity.ok(updatedPayment);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
        paymentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
