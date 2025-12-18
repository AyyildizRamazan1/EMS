package com.ramazanayyildiz.EMS.dto;

import com.ramazanayyildiz.EMS.entity.enums.InventoryStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InventoryUpdateDto {

    private String name;
    private String serialNumber;
    private LocalDate purchaseDate;
    private LocalDate warrantyEndDate;
    private InventoryStatus currentStatus;
}
