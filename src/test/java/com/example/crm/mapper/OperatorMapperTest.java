package com.example.crm.mapper;

import com.example.crm.dto.OperatorDto;
import com.example.crm.entity.Operator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class OperatorMapperTest {

    @Autowired
    private OperatorMapper operatorMapper;

    @Test
    void shouldMapOperatorToDto() {
        // given
        Operator operator = new Operator(1L, "John Doe", Collections.emptyList());

        // when
        OperatorDto operatorDto = operatorMapper.toDto(operator);

        // then
        assertNotNull(operatorDto);
        assertEquals(operator.getId(), operatorDto.getId());
        assertEquals(operator.getFullName(), operatorDto.getFullName());
    }

    @Test
    void shouldMapDtoToOperator() {
        // given
        OperatorDto operatorDto = new OperatorDto();
        operatorDto.setId(1L);
        operatorDto.setFullName("John Doe");

        // when
        Operator operator = operatorMapper.toEntity(operatorDto);

        // then
        assertNotNull(operator);
        assertEquals(operatorDto.getId(), operator.getId());
        assertEquals(operatorDto.getFullName(), operator.getFullName());
    }

    @Test
    void shouldMapOperatorListToDtoList() {
        // given
        Operator operator1 = new Operator(1L, "John Doe", Collections.emptyList());
        Operator operator2 = new Operator(2L, "Jane Smith", Collections.emptyList());
        List<Operator> operators = List.of(operator1, operator2);

        // when
        List<OperatorDto> operatorDtos = operatorMapper.toDtoList(operators);

        // then
        assertNotNull(operatorDtos);
        assertEquals(2, operatorDtos.size());
        assertEquals(operator1.getFullName(), operatorDtos.get(0).getFullName());
        assertEquals(operator2.getFullName(), operatorDtos.get(1).getFullName());
    }
}
