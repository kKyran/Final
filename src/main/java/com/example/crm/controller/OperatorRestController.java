package com.example.crm.controller;

import com.example.crm.dto.OperatorRequestDto;
import com.example.crm.dto.OperatorResponseDto;
import com.example.crm.service.OperatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/operators")
@RequiredArgsConstructor
public class OperatorRestController {

    private final OperatorService operatorService;

    @GetMapping
    public ResponseEntity<List<OperatorResponseDto>> getAllOperators() {
        return ResponseEntity.ok(operatorService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OperatorResponseDto> getOperatorById(@PathVariable Long id) {
        return operatorService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OperatorResponseDto> createOperator(@RequestBody OperatorRequestDto operatorRequestDto) {
        OperatorResponseDto createdOperator = operatorService.create(operatorRequestDto);
        return new ResponseEntity<>(createdOperator, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<OperatorResponseDto> updateOperator(@PathVariable Long id, @RequestBody OperatorRequestDto operatorRequestDto) {
        OperatorResponseDto updatedOperator = operatorService.update(id, operatorRequestDto);
        return ResponseEntity.ok(updatedOperator);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOperator(@PathVariable Long id) {
        operatorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
