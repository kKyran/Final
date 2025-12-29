package com.example.crm.mapper;

import com.example.crm.dto.LessonRequestDto;
import com.example.crm.dto.LessonResponseDto;
import com.example.crm.entity.Lesson;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GroupMapper.class})
public interface LessonMapper {

    LessonResponseDto toResponseDto(Lesson lesson);

    List<LessonResponseDto> toResponseDtoList(List<Lesson> lessons);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    Lesson toEntity(LessonRequestDto lessonRequestDto);
}
