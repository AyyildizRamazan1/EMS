package com.ramazanayyildiz.EMS.dto;

import com.ramazanayyildiz.EMS.entity.enums.InventoryStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InventoryResponseDto {

    private Long id;
    private String name;
    private String serialNumber;
    private LocalDate purchaseDate;
    private LocalDate warrantyEndDate;
    private InventoryStatus currentStatus;
    private LocalDateTime createdTime;

    //Kaydeden kullanıcının sadece temel bilgileri
    private Long registeredById;
    private String registeredByUsername;
}
