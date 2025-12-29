package com.example.crm.mapper;

import com.example.crm.dto.PaymentRequestDto;
import com.example.crm.dto.PaymentResponseDto;
import com.example.crm.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {StudentMapper.class, CourseMapper.class})
public interface PaymentMapper {

    PaymentResponseDto toResponseDto(Payment payment);

    List<PaymentResponseDto> toResponseDtoList(List<Payment> payments);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "student", ignore = true)
    @Mapping(target = "course", ignore = true)
    Payment toEntity(PaymentRequestDto paymentRequestDto);
}
