package com.example.crm.controller;

import com.example.crm.dto.ApplicationRequestRequestDto;
import com.example.crm.dto.ApplicationRequestResponseDto;
import com.example.crm.service.ApplicationRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/requests")
@RequiredArgsConstructor
public class ApplicationRequestRestController {

    private final ApplicationRequestService requestService;

    @GetMapping
    public ResponseEntity<List<ApplicationRequestResponseDto>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAll());
    }

    @GetMapping("/unhandled")
    public ResponseEntity<List<ApplicationRequestResponseDto>> getUnhandledRequests() {
        return ResponseEntity.ok(requestService.getUnhandled());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationRequestResponseDto> getRequestById(@PathVariable Long id) {
        return requestService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ApplicationRequestResponseDto> createRequest(@RequestBody ApplicationRequestRequestDto requestDto) {
        ApplicationRequestResponseDto createdRequest = requestService.create(requestDto);
        return new ResponseEntity<>(createdRequest, HttpStatus.CREATED);
    }

    @PutMapping("/{requestId}/assign/{operatorId}")
    public ResponseEntity<ApplicationRequestResponseDto> assignOperator(
            @PathVariable Long requestId,
            @PathVariable Long operatorId) {
        ApplicationRequestResponseDto updatedRequest = requestService.assignOperator(requestId, operatorId);
        return ResponseEntity.ok(updatedRequest);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        requestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
