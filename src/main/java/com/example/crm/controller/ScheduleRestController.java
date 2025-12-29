package com.example.crm.controller;

import com.example.crm.dto.ScheduleRequestDto;
import com.example.crm.dto.ScheduleResponseDto;
import com.example.crm.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleRestController {

    private final ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<List<ScheduleResponseDto>> getAllSchedules() {
        return ResponseEntity.ok(scheduleService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> getScheduleById(@PathVariable Long id) {
        return scheduleService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ScheduleResponseDto> createSchedule(@RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto createdSchedule = scheduleService.create(scheduleRequestDto);
        return new ResponseEntity<>(createdSchedule, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ScheduleResponseDto> updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto scheduleRequestDto) {
        ScheduleResponseDto updatedSchedule = scheduleService.update(id, scheduleRequestDto);
        return ResponseEntity.ok(updatedSchedule);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        scheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
