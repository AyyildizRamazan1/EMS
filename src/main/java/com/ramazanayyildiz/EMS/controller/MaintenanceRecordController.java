package com.ramazanayyildiz.EMS.controller;

import com.ramazanayyildiz.EMS.dto.MaintenanceRecordCreateDto;
import com.ramazanayyildiz.EMS.dto.MaintenanceRecordResponseDto;
import com.ramazanayyildiz.EMS.service.MaintenanceRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@RequiredArgsConstructor
public class MaintenanceRecordController {

    private final MaintenanceRecordService maintenanceRecordService;

    @PostMapping
    @PreAuthorize("hasRole('TECHNICIAN')")
    public ResponseEntity<MaintenanceRecordResponseDto> create(
            @RequestBody MaintenanceRecordCreateDto dto,
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.status(HttpStatus.CREATED).body(maintenanceRecordService.createRecord(dto, userDetails.getUsername()));
    }

    @PatchMapping("/{id}/complete")
    @PreAuthorize("hasRole('TECHNICIAN')")
    public ResponseEntity<MaintenanceRecordResponseDto> complete(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceRecordService.completeMaintenance(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('BOSS', 'TECHNICIAN')")
    public ResponseEntity<List<MaintenanceRecordResponseDto>> getAll() {
        return ResponseEntity.ok(maintenanceRecordService.getAllRecords());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceRecordResponseDto> getById(@PathVariable Long id) {
        return ResponseEntity.ok(maintenanceRecordService.getRecordById(id));
    }

}
