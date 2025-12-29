package com.example.crm.service;

import com.example.crm.dto.OperatorRequestDto;
import com.example.crm.dto.OperatorResponseDto;
import com.example.crm.entity.Operator;
import com.example.crm.mapper.OperatorMapper;
import com.example.crm.repository.OperatorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OperatorServiceTest {

    @Mock
    private OperatorRepository operatorRepository;

    @Mock
    private OperatorMapper operatorMapper;

    @InjectMocks
    private OperatorService operatorService;

    @Test
    void getAll() {
        when(operatorRepository.findAll()).thenReturn(Collections.singletonList(new Operator()));
        when(operatorMapper.toResponseDtoList(any())).thenReturn(Collections.singletonList(new OperatorResponseDto()));

        var result = operatorService.getAll();

        assertEquals(1, result.size());
        verify(operatorRepository).findAll();
        verify(operatorMapper).toResponseDtoList(any());
    }

    @Test
    void getById_found() {
        Long operatorId = 1L;
        when(operatorRepository.findById(operatorId)).thenReturn(Optional.of(new Operator()));
        when(operatorMapper.toResponseDto(any(Operator.class))).thenReturn(new OperatorResponseDto());

        var result = operatorService.getById(operatorId);

        assertTrue(result.isPresent());
        verify(operatorRepository).findById(operatorId);
        verify(operatorMapper).toResponseDto(any(Operator.class));
    }

    @Test
    void create() {
        OperatorRequestDto request = new OperatorRequestDto();
        request.setFullName("John Doe");

        when(operatorMapper.toEntity(any(OperatorRequestDto.class))).thenReturn(new Operator());
        when(operatorRepository.save(any(Operator.class))).thenAnswer(i -> i.getArguments()[0]);
        when(operatorMapper.toResponseDto(any(Operator.class))).thenReturn(new OperatorResponseDto());

        operatorService.create(request);

        verify(operatorRepository).save(any(Operator.class));
    }

    @Test
    void update() {
        Long operatorId = 1L;
        OperatorRequestDto request = new OperatorRequestDto();
        request.setFullName("Jane Doe");

        Operator existingOperator = new Operator();
        existingOperator.setId(operatorId);
        existingOperator.setFullName("John Doe");

        when(operatorRepository.findById(operatorId)).thenReturn(Optional.of(existingOperator));
        when(operatorRepository.save(any(Operator.class))).thenAnswer(i -> i.getArguments()[0]);
        when(operatorMapper.toResponseDto(any(Operator.class))).thenReturn(new OperatorResponseDto());

        operatorService.update(operatorId, request);

        verify(operatorRepository).save(existingOperator);
        assertEquals("Jane Doe", existingOperator.getFullName());
    }

    @Test
    void delete() {
        Long operatorId = 1L;
        doNothing().when(operatorRepository).deleteById(operatorId);

        operatorService.delete(operatorId);

        verify(operatorRepository).deleteById(operatorId);
    }
}
