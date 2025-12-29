package com.example.crm.service;

import com.example.crm.dto.GroupRequestDto;
import com.example.crm.dto.GroupResponseDto;
import com.example.crm.entity.Course;
import com.example.crm.entity.Group;
import com.example.crm.entity.Student;
import com.example.crm.entity.Teacher;
import com.example.crm.mapper.GroupMapper;
import com.example.crm.repository.CourseRepository;
import com.example.crm.repository.GroupRepository;
import com.example.crm.repository.StudentRepository;
import com.example.crm.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {

    @Mock
    private GroupRepository groupRepository;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private TeacherRepository teacherRepository;
    @Mock
    private StudentRepository studentRepository;
    @Mock
    private GroupMapper groupMapper;

    @InjectMocks
    private GroupService groupService;

    @Test
    void getAll() {
        when(groupRepository.findAll()).thenReturn(Collections.singletonList(new Group()));
        when(groupMapper.toResponseDtoList(any())).thenReturn(Collections.singletonList(new GroupResponseDto()));

        var result = groupService.getAll();

        assertEquals(1, result.size());
        verify(groupRepository).findAll();
    }

    @Test
    void create() {
        GroupRequestDto request = new GroupRequestDto();
        request.setCourseId(1L);
        request.setTeacherId(1L);
        request.setStudentIds(Set.of(1L));

        when(groupMapper.toEntity(request)).thenReturn(new Group());
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course()));
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(new Teacher()));
        when(studentRepository.findAllById(Set.of(1L))).thenReturn(Collections.singletonList(new Student()));
        when(groupRepository.save(any(Group.class))).thenAnswer(i -> i.getArguments()[0]);
        when(groupMapper.toResponseDto(any(Group.class))).thenReturn(new GroupResponseDto());

        groupService.create(request);

        verify(groupRepository).save(any(Group.class));
    }
    
    @Test
    void update() {
        Long groupId = 1L;
        GroupRequestDto request = new GroupRequestDto();
        request.setName("New Name");
        request.setCourseId(1L);
        request.setTeacherId(1L);
        request.setStudentIds(new HashSet<>());

        Group existingGroup = new Group();
        when(groupRepository.findById(groupId)).thenReturn(Optional.of(existingGroup));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(new Course()));
        when(teacherRepository.findById(1L)).thenReturn(Optional.of(new Teacher()));
        when(studentRepository.findAllById(any())).thenReturn(Collections.emptyList());
        when(groupRepository.save(any(Group.class))).thenAnswer(i -> i.getArguments()[0]);
        when(groupMapper.toResponseDto(any(Group.class))).thenReturn(new GroupResponseDto());

        groupService.update(groupId, request);

        verify(groupRepository).save(existingGroup);
        assertEquals("New Name", existingGroup.getName());
    }

    @Test
    void delete() {
        Long groupId = 1L;
        doNothing().when(groupRepository).deleteById(groupId);
        
        groupService.delete(groupId);
        
        verify(groupRepository).deleteById(groupId);
    }
}
