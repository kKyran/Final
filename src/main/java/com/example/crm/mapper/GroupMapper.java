package com.example.crm.mapper;

import com.example.crm.dto.GroupRequestDto;
import com.example.crm.dto.GroupResponseDto;
import com.example.crm.entity.Group;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CourseMapper.class, TeacherMapper.class, StudentMapper.class})
public interface GroupMapper {

    GroupResponseDto toResponseDto(Group group);

    List<GroupResponseDto> toResponseDtoList(List<Group> groups);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "course", ignore = true)
    @Mapping(target = "teacher", ignore = true)
    @Mapping(target = "students", ignore = true)
    Group toEntity(GroupRequestDto groupRequestDto);
}
