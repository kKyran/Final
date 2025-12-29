package com.example.crm.service;

import com.example.crm.dto.LessonRequestDto;
import com.example.crm.dto.LessonResponseDto;
import com.example.crm.entity.Group;
import com.example.crm.entity.Lesson;
import com.example.crm.mapper.LessonMapper;
import com.example.crm.repository.GroupRepository;
import com.example.crm.repository.LessonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LessonService {

    private final LessonRepository lessonRepository;
    private final GroupRepository groupRepository;
    private final LessonMapper lessonMapper;

    public List<LessonResponseDto> getAll() {
        return lessonMapper.toResponseDtoList(lessonRepository.findAll());
    }

    public Optional<LessonResponseDto> getById(Long id) {
        return lessonRepository.findById(id).map(lessonMapper::toResponseDto);
    }

    @Transactional
    public LessonResponseDto create(LessonRequestDto requestDto) {
        Lesson lesson = lessonMapper.toEntity(requestDto);

        Group group = groupRepository.findById(requestDto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
        lesson.setGroup(group);

        Lesson savedLesson = lessonRepository.save(lesson);
        return lessonMapper.toResponseDto(savedLesson);
    }

    @Transactional
    public LessonResponseDto update(Long id, LessonRequestDto requestDto) {
        Lesson existingLesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        existingLesson.setTopic(requestDto.getTopic());
        existingLesson.setDateTime(requestDto.getDateTime());

        Group group = groupRepository.findById(requestDto.getGroupId())
                .orElseThrow(() -> new RuntimeException("Group not found"));
        existingLesson.setGroup(group);

        Lesson updatedLesson = lessonRepository.save(existingLesson);
        return lessonMapper.toResponseDto(updatedLesson);
    }

    @Transactional
    public void delete(Long id) {
        lessonRepository.deleteById(id);
    }
}
