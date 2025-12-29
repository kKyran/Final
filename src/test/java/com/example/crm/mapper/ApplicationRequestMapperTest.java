package com.example.crm.mapper;

import com.example.crm.dto.ApplicationRequestDto;
import com.example.crm.dto.CourseDto;
import com.example.crm.dto.OperatorDto;
import com.example.crm.entity.ApplicationRequest;
import com.example.crm.entity.Course;
import com.example.crm.entity.Operator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ApplicationRequestMapperTest {

    @Autowired
    private ApplicationRequestMapper applicationRequestMapper;

    @Test
    void shouldMapApplicationRequestToDto() {
        // given
        Course course = new Course(1L, "Java Development", Collections.emptyList());
        Operator operator = new Operator(1L, "John Doe", Collections.emptyList());
        ApplicationRequest request = new ApplicationRequest(1L, "Test User", "Comment", "123", false, course, operator);

        // when
        ApplicationRequestDto dto = applicationRequestMapper.toDto(request);

        // then
        assertNotNull(dto);
        assertEquals(request.getId(), dto.getId());
        assertEquals(request.getUserName(), dto.getUserName());
        assertNotNull(dto.getCourse());
        assertEquals(course.getName(), dto.getCourse().getName());
        assertNotNull(dto.getOperator());
        assertEquals(operator.getFullName(), dto.getOperator().getFullName());
    }

    @Test
    void shouldMapDtoToApplicationRequest() {
        // given
        CourseDto courseDto = new CourseDto();
        courseDto.setId(1L);
        courseDto.setName("Java Development");

        OperatorDto operatorDto = new OperatorDto();
        operatorDto.setId(1L);
        operatorDto.setFullName("John Doe");

        ApplicationRequestDto dto = new ApplicationRequestDto();
        dto.setId(1L);
        dto.setUserName("Test User");
        dto.setCourse(courseDto);
        dto.setOperator(operatorDto);


        // when
        ApplicationRequest request = applicationRequestMapper.toEntity(dto);

        // then
        assertNotNull(request);
        assertEquals(dto.getId(), request.getId());
        assertEquals(dto.getUserName(), request.getUserName());
        assertNotNull(request.getCourse());
        assertEquals(courseDto.getName(), request.getCourse().getName());
        assertNotNull(request.getOperator());
        assertEquals(operatorDto.getFullName(), request.getOperator().getFullName());
    }
}
