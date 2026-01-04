package com.ramazanayyildiz.EMS.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MaintenanceRecordResponseDto {
    private Long id;
    private Long inventoryId;
    private String inventoryName;
    private String technicianUsername;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String description;
    private BigDecimal totalCost;
    private Boolean isCompleted;
    private LocalDateTime createdTime;

}
