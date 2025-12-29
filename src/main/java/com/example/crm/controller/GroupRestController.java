package com.example.crm.controller;

import com.example.crm.dto.GroupRequestDto;
import com.example.crm.dto.GroupResponseDto;
import com.example.crm.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupRestController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupResponseDto>> getAllGroups() {
        return ResponseEntity.ok(groupService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResponseDto> getGroupById(@PathVariable Long id) {
        return groupService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<GroupResponseDto> createGroup(@RequestBody GroupRequestDto groupRequestDto) {
        GroupResponseDto createdGroup = groupService.create(groupRequestDto);
        return new ResponseEntity<>(createdGroup, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupResponseDto> updateGroup(@PathVariable Long id, @RequestBody GroupRequestDto groupRequestDto) {
        GroupResponseDto updatedGroup = groupService.update(id, groupRequestDto);
        return ResponseEntity.ok(updatedGroup);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable Long id) {
        groupService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
