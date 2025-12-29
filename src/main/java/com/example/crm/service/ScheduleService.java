package com.example.crm.service;

import com.example.crm.dto.ScheduleRequestDto;
import com.example.crm.dto.ScheduleResponseDto;
import com.example.crm.entity.Group;
import com.example.crm.entity.Schedule;
import com.example.crm.mapper.ScheduleMapper;
import com.example.crm.repository.GroupRepository;
import com.example.crm.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final GroupRepository groupRepository;
    private final ScheduleMapper scheduleMapper;

    public List<ScheduleResponseDto> getAll() {
        return scheduleMapper.toResponseDtoList(scheduleRepository.findAll());
    }

    public Optional<ScheduleResponseDto> getById(Long id) {
        return scheduleRepository.findById(id).map(scheduleMapper::toResponseDto);
    }

    @Transactional
    public ScheduleResponseDto create(ScheduleRequestDto requestDto) {
        Schedule schedule = scheduleMapper.toEntity(requestDto);

        Group group = groupRepository.findById(requestDto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
        schedule.setGroup(group);

        Schedule savedSchedule = scheduleRepository.save(schedule);
        return scheduleMapper.toResponseDto(savedSchedule);
    }

    @Transactional
    public ScheduleResponseDto update(Long id, ScheduleRequestDto requestDto) {
        Schedule existingSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Schedule not found"));

        existingSchedule.setDayOfWeek(requestDto.getDayOfWeek());
        existingSchedule.setStartTime(requestDto.getStartTime());
        existingSchedule.setEndTime(requestDto.getEndTime());

        Group group = groupRepository.findById(requestDto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
        existingSchedule.setGroup(group);

        Schedule updatedSchedule = scheduleRepository.save(existingSchedule);
        return scheduleMapper.toResponseDto(updatedSchedule);
    }

    @Transactional
    public void delete(Long id) {
        scheduleRepository.deleteById(id);
    }
}
