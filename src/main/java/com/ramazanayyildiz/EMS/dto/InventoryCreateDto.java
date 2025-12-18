package com.ramazanayyildiz.EMS.dto;

import com.ramazanayyildiz.EMS.entity.enums.InventoryStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class InventoryCreateDto {

    @NotBlank(message = "Envanter adı boş bırakılmaz")
    private String name;

    @NotBlank(message = "Seri numara boş bırakılamaz")
    private String serialNumber;

    @NotNull(message = "Satın alma tarihi zorunludur")
    private LocalDate purchaseDate;

    private LocalDate warrantyEndDate;

    @NotNull(message = "Mevcut durum belirtilmelidir")
    private InventoryStatus currentStatus;
}
