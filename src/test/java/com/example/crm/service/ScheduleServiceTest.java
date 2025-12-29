package com.example.crm.service;

import com.example.crm.dto.ScheduleRequestDto;
import com.example.crm.dto.ScheduleResponseDto;
import com.example.crm.entity.Group;
import com.example.crm.entity.Schedule;
import com.example.crm.mapper.ScheduleMapper;
import com.example.crm.repository.GroupRepository;
import com.example.crm.repository.ScheduleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private ScheduleMapper scheduleMapper;

    @InjectMocks
    private ScheduleService scheduleService;

    @Test
    void getAll() {
        when(scheduleRepository.findAll()).thenReturn(Collections.singletonList(new Schedule()));
        when(scheduleMapper.toResponseDtoList(any())).thenReturn(Collections.singletonList(new ScheduleResponseDto()));

        var result = scheduleService.getAll();

        assertEquals(1, result.size());
        verify(scheduleRepository).findAll();
    }

    @Test
    void create() {
        ScheduleRequestDto request = new ScheduleRequestDto();
        request.setGroupId(1L);

        when(scheduleMapper.toEntity(request)).thenReturn(new Schedule());
        when(groupRepository.findById(1L)).thenReturn(Optional.of(new Group()));
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(i -> i.getArguments()[0]);
        when(scheduleMapper.toResponseDto(any(Schedule.class))).thenReturn(new ScheduleResponseDto());

        scheduleService.create(request);

        verify(scheduleRepository).save(any(Schedule.class));
    }
    
    @Test
    void update() {
        Long scheduleId = 1L;
        ScheduleRequestDto request = new ScheduleRequestDto();
        request.setDayOfWeek(DayOfWeek.MONDAY);
        request.setStartTime(LocalTime.of(9, 0));
        request.setEndTime(LocalTime.of(10, 0));
        request.setGroupId(1L);

        Schedule existingSchedule = new Schedule();
        existingSchedule.setId(scheduleId);

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(existingSchedule));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(new Group()));
        when(scheduleRepository.save(any(Schedule.class))).thenAnswer(i -> i.getArguments()[0]);
        when(scheduleMapper.toResponseDto(any(Schedule.class))).thenReturn(new ScheduleResponseDto());

        scheduleService.update(scheduleId, request);

        verify(scheduleRepository).save(existingSchedule);
        assertEquals(DayOfWeek.MONDAY, existingSchedule.getDayOfWeek());
    }

    @Test
    void delete() {
        Long scheduleId = 1L;
        doNothing().when(scheduleRepository).deleteById(scheduleId);
        
        scheduleService.delete(scheduleId);
        
        verify(scheduleRepository).deleteById(scheduleId);
    }
}
