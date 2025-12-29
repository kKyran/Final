package com.example.crm.mapper;

import com.example.crm.dto.ApplicationRequestRequestDto;
import com.example.crm.dto.ApplicationRequestResponseDto;
import com.example.crm.entity.ApplicationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CourseMapper.class, OperatorMapper.class})
public interface ApplicationRequestMapper {
    ApplicationRequestResponseDto toResponseDto(ApplicationRequest applicationRequest);
    List<ApplicationRequestResponseDto> toResponseDtoList(List<ApplicationRequest> applicationRequests);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "handled", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "operator", ignore = true)
    ApplicationRequest toEntity(ApplicationRequestRequestDto requestDto);
}
