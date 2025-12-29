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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApplicationRequestService {
    private final ApplicationRequestRepository requestRepository;
    private final CourseRepository courseRepository;
    private final OperatorRepository operatorRepository;
    private final ApplicationRequestMapper requestMapper;

    public List<ApplicationRequestResponseDto> getAll() {
        return requestMapper.toResponseDtoList(requestRepository.findAll());
    }

    public List<ApplicationRequestResponseDto> getUnhandled() {
        return requestMapper.toResponseDtoList(requestRepository.findAllByHandledIsFalse());
    }

    public Optional<ApplicationRequestResponseDto> getById(Long id) {
        return requestRepository.findById(id).map(requestMapper::toResponseDto);
    }

    @Transactional
    public ApplicationRequestResponseDto create(ApplicationRequestRequestDto requestDto) {
        ApplicationRequest applicationRequest = requestMapper.toEntity(requestDto);

        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found")); // Proper exception
        applicationRequest.setCourse(course);
        applicationRequest.setHandled(false);

        ApplicationRequest savedRequest = requestRepository.save(applicationRequest);
        return requestMapper.toResponseDto(savedRequest);
    }

    @Transactional
    public ApplicationRequestResponseDto assignOperator(Long requestId, Long operatorId) {
        ApplicationRequest request = requestRepository.findById(requestId)
                .orElseThrow(() -> new RuntimeException("ApplicationRequest not found"));
        Operator operator = operatorRepository.findById(operatorId)
                .orElseThrow(() -> new RuntimeException("Operator not found"));

        request.setOperator(operator);
        request.setHandled(true);

        ApplicationRequest savedRequest = requestRepository.save(request);
        return requestMapper.toResponseDto(savedRequest);
    }

    @Transactional
    public void delete(Long id) {
        requestRepository.deleteById(id);
    }
}
