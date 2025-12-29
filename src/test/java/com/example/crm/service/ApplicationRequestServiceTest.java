package com.example.crm.service;

import com.example.crm.dto.ApplicationRequestRequestDto;
import com.example.crm.dto.ApplicationRequestResponseDto;
import com.example.crm.entity.ApplicationRequest;
import com.example.crm.entity.Course;
import com.example.crm.entity.Operator;
import com.example.crm.mapper.ApplicationRequestMapper;
import com.example.crm.repository.ApplicationRequestRepository;
import com.example.crm.repository.CourseRepository;
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
class ApplicationRequestServiceTest {

    @Mock
    private ApplicationRequestRepository requestRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private OperatorRepository operatorRepository;
    @Mock
    private ApplicationRequestMapper requestMapper;

    @InjectMocks
    private ApplicationRequestService requestService;

    @Test
    void getAll() {
        when(requestRepository.findAll()).thenReturn(Collections.singletonList(new ApplicationRequest()));
        when(requestMapper.toResponseDtoList(any())).thenReturn(Collections.singletonList(new ApplicationRequestResponseDto()));

        var result = requestService.getAll();

        assertEquals(1, result.size());
        verify(requestRepository).findAll();
    }

    @Test
    void getUnhandled() {
        when(requestRepository.findAllByHandledIsFalse()).thenReturn(Collections.singletonList(new ApplicationRequest()));
        when(requestMapper.toResponseDtoList(any())).thenReturn(Collections.singletonList(new ApplicationRequestResponseDto()));

        var result = requestService.getUnhandled();
        
        assertEquals(1, result.size());
        verify(requestRepository).findAllByHandledIsFalse();
    }

    @Test
    void create() {
        ApplicationRequestRequestDto requestDto = new ApplicationRequestRequestDto();
        requestDto.setCourseId(1L);

        when(requestMapper.toEntity(any())).thenReturn(new ApplicationRequest());
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course()));
        when(requestRepository.save(any(ApplicationRequest.class))).thenAnswer(i -> i.getArguments()[0]);
        when(requestMapper.toResponseDto(any(ApplicationRequest.class))).thenReturn(new ApplicationRequestResponseDto());
        
        requestService.create(requestDto);

        verify(requestRepository).save(any(ApplicationRequest.class));
    }

    @Test
    void assignOperator() {
        Long requestId = 1L;
        Long operatorId = 1L;
        ApplicationRequest request = new ApplicationRequest();
        Operator operator = new Operator();

        when(requestRepository.findById(requestId)).thenReturn(Optional.of(request));
        when(operatorRepository.findById(operatorId)).thenReturn(Optional.of(operator));
        when(requestRepository.save(any(ApplicationRequest.class))).thenAnswer(i -> i.getArguments()[0]);
        when(requestMapper.toResponseDto(any(ApplicationRequest.class))).thenReturn(new ApplicationRequestResponseDto());

        requestService.assignOperator(requestId, operatorId);

        verify(requestRepository).save(request);
        assertTrue(request.isHandled());
        assertEquals(operator, request.getOperator());
    }

    @Test
    void delete() {
        Long requestId = 1L;
        doNothing().when(requestRepository).deleteById(requestId);

        requestService.delete(requestId);

        verify(requestRepository).deleteById(requestId);
    }
}
