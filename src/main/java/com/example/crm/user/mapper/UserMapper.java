package com.example.crm.user.mapper;

import com.example.crm.user.dto.UserResponseDto;
import com.example.crm.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", expression = "java(user.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))")
    UserResponseDto toUserResponseDto(User user);
}
