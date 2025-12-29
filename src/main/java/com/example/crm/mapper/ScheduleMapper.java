package com.example.crm.mapper;

import com.example.crm.dto.ScheduleRequestDto;
import com.example.crm.dto.ScheduleResponseDto;
import com.example.crm.entity.Schedule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GroupMapper.class})
public interface ScheduleMapper {

    ScheduleResponseDto toResponseDto(Schedule schedule);

    List<ScheduleResponseDto> toResponseDtoList(List<Schedule> schedules);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "group", ignore = true)
    Schedule toEntity(ScheduleRequestDto scheduleRequestDto);
}
