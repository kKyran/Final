package com.example.crm.service;

import com.example.crm.dto.LessonRequestDto;
import com.example.crm.dto.LessonResponseDto;
import com.example.crm.entity.Group;
import com.example.crm.entity.Lesson;
import com.example.crm.mapper.LessonMapper;
import com.example.crm.repository.GroupRepository;
import com.example.crm.repository.LessonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {

    @Mock
    private LessonRepository lessonRepository;
    @Mock
    private GroupRepository groupRepository;
    @Mock
    private LessonMapper lessonMapper;

    @InjectMocks
    private LessonService lessonService;

    @Test
    void getAll() {
        when(lessonRepository.findAll()).thenReturn(Collections.singletonList(new Lesson()));
        when(lessonMapper.toResponseDtoList(any())).thenReturn(Collections.singletonList(new LessonResponseDto()));

        var result = lessonService.getAll();

        assertEquals(1, result.size());
        verify(lessonRepository).findAll();
    }

    @Test
    void create() {
        LessonRequestDto request = new LessonRequestDto();
        request.setGroupId(1L);

        when(lessonMapper.toEntity(request)).thenReturn(new Lesson());
        when(groupRepository.findById(1L)).thenReturn(Optional.of(new Group()));
        when(lessonRepository.save(any(Lesson.class))).thenAnswer(i -> i.getArguments()[0]);
        when(lessonMapper.toResponseDto(any(Lesson.class))).thenReturn(new LessonResponseDto());

        lessonService.create(request);

        verify(lessonRepository).save(any(Lesson.class));
    }
    
    @Test
    void update() {
        Long lessonId = 1L;
        LessonRequestDto request = new LessonRequestDto();
        request.setTopic("New Topic");
        request.setDateTime(LocalDateTime.now());
        request.setGroupId(1L);

        Lesson existingLesson = new Lesson();
        existingLesson.setId(lessonId);

        when(lessonRepository.findById(lessonId)).thenReturn(Optional.of(existingLesson));
        when(groupRepository.findById(1L)).thenReturn(Optional.of(new Group()));
        when(lessonRepository.save(any(Lesson.class))).thenAnswer(i -> i.getArguments()[0]);
        when(lessonMapper.toResponseDto(any(Lesson.class))).thenReturn(new LessonResponseDto());

        lessonService.update(lessonId, request);

        verify(lessonRepository).save(existingLesson);
        assertEquals("New Topic", existingLesson.getTopic());
    }

    @Test
    void delete() {
        Long lessonId = 1L;
        doNothing().when(lessonRepository).deleteById(lessonId);
        
        lessonService.delete(lessonId);
        
        verify(lessonRepository).deleteById(lessonId);
    }
}
