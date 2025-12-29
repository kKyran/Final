package com.example.crm.service;

import com.example.crm.dto.OperatorRequestDto;
import com.example.crm.dto.OperatorResponseDto;
import com.example.crm.entity.Operator;
import com.example.crm.mapper.OperatorMapper;
import com.example.crm.repository.OperatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OperatorService {
    private final OperatorRepository operatorRepository;
    private final OperatorMapper operatorMapper;

    public List<OperatorResponseDto> getAll() {
        return operatorMapper.toResponseDtoList(operatorRepository.findAll());
    }

    public Optional<OperatorResponseDto> getById(Long id) {
        return operatorRepository.findById(id).map(operatorMapper::toResponseDto);
    }

    @Transactional
    public OperatorResponseDto create(OperatorRequestDto operatorRequestDto) {
        Operator operator = operatorMapper.toEntity(operatorRequestDto);
        Operator savedOperator = operatorRepository.save(operator);
        return operatorMapper.toResponseDto(savedOperator);
    }

    @Transactional
    public OperatorResponseDto update(Long id, OperatorRequestDto operatorRequestDto) {
        Operator existingOperator = operatorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Operator not found")); // Replace with a proper exception
        existingOperator.setFullName(operatorRequestDto.getFullName());
        Operator updatedOperator = operatorRepository.save(existingOperator);
        return operatorMapper.toResponseDto(updatedOperator);
    }

    @Transactional
    public void delete(Long id) {
        operatorRepository.deleteById(id);
    }
}
