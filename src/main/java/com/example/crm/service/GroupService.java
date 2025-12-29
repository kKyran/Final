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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;
    private final CourseRepository courseRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final GroupMapper groupMapper;

    public List<GroupResponseDto> getAll() {
        return groupMapper.toResponseDtoList(groupRepository.findAll());
    }

    public Optional<GroupResponseDto> getById(Long id) {
        return groupRepository.findById(id).map(groupMapper::toResponseDto);
    }

    @Transactional
    public GroupResponseDto create(GroupRequestDto requestDto) {
        Group group = groupMapper.toEntity(requestDto);

        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        group.setCourse(course);

        Teacher teacher = teacherRepository.findById(requestDto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        group.setTeacher(teacher);

        if (requestDto.getStudentIds() != null && !requestDto.getStudentIds().isEmpty()) {
            Set<Student> students = new HashSet<>(studentRepository.findAllById(requestDto.getStudentIds()));
            group.setStudents(students);
        }

        Group savedGroup = groupRepository.save(group);
        return groupMapper.toResponseDto(savedGroup);
    }

    @Transactional
    public GroupResponseDto update(Long id, GroupRequestDto requestDto) {
        Group existingGroup = groupRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Group not found"));

        existingGroup.setName(requestDto.getName());

        Course course = courseRepository.findById(requestDto.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));
        existingGroup.setCourse(course);

        Teacher teacher = teacherRepository.findById(requestDto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        existingGroup.setTeacher(teacher);

        if (requestDto.getStudentIds() != null) {
            Set<Student> students = new HashSet<>(studentRepository.findAllById(requestDto.getStudentIds()));
            existingGroup.setStudents(students);
        }

        Group updatedGroup = groupRepository.save(existingGroup);
        return groupMapper.toResponseDto(updatedGroup);
    }

    @Transactional
    public void delete(Long id) {
        groupRepository.deleteById(id);
    }
}
