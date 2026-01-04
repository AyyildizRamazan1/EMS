package com.ramazanayyildiz.EMS.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class MaintenanceRecordCreateDto {
    private Long inventoryId;
    private String description;
    private LocalDateTime startDate;
    private BigDecimal totalCost;
    //technicianId jwt üserinden otomatik alınacak
}
