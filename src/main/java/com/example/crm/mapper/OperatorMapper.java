package com.example.crm.mapper;

import com.example.crm.dto.OperatorRequestDto;
import com.example.crm.dto.OperatorResponseDto;
import com.example.crm.entity.Operator;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OperatorMapper {
    OperatorResponseDto toResponseDto(Operator operator);
    List<OperatorResponseDto> toResponseDtoList(List<Operator> operators);

    @Mapping(target = "id", ignore = true)
    Operator toEntity(OperatorRequestDto operatorRequestDto);
}
