package com.example.crm.mapper;

import com.example.crm.dto.CourseDto;
import com.example.crm.entity.Course;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CourseMapperTest {

    @Autowired
    private CourseMapper courseMapper;

    @Test
    void shouldMapCourseToDto() {
        // given
        Course course = new Course(1L, "Java Development", Collections.emptyList());

        // when
        CourseDto courseDto = courseMapper.toDto(course);

        // then
        assertNotNull(courseDto);
        assertEquals(course.getId(), courseDto.getId());
        assertEquals(course.getName(), course.getName());
    }

    @Test
    void shouldMapDtoToCourse() {
        // given
        CourseDto courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setName("Java Development");

        // when
        Course course = courseMapper.toEntity(courseDto);

        // then
        assertNotNull(course);
        assertEquals(courseDto.getId(), course.getId());
        assertEquals(courseDto.getName(), course.getName());
        assertNull(course.getRequests()); // Mapper doesn't map collections by default
    }

    @Test
    void shouldMapCourseListToDtoList() {
        // given
        Course course1 = new Course(1L, "Java Development", Collections.emptyList());
        Course course2 = new Course(2L, "Frontend Development", Collections.emptyList());
        List<Course> courses = List.of(course1, course2);

        // when
        List<CourseDto> courseDtos = courseMapper.toDtoList(courses);

        // then
        assertNotNull(courseDtos);
        assertEquals(2, courseDtos.size());
        assertEquals(course1.getName(), courseDtos.get(0).getName());
        assertEquals(course2.getName(), courseDtos.get(1).getName());
    }
}
