package com.ramazanayyildiz.EMS.service;

import com.ramazanayyildiz.EMS.dto.InventoryCreateDto;
import com.ramazanayyildiz.EMS.dto.InventoryResponseDto;
import com.ramazanayyildiz.EMS.dto.InventoryUpdateDto;
import com.ramazanayyildiz.EMS.entity.Inventory;
import com.ramazanayyildiz.EMS.entity.User;
import com.ramazanayyildiz.EMS.exception.ResourceNotFoundException;
import com.ramazanayyildiz.EMS.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final UserService userService;

    //Entity -> Response DTO
    private InventoryResponseDto convertToDto(Inventory inventory) {
        return new InventoryResponseDto(
                inventory.getId(),
                inventory.getName(),
                inventory.getSerialNumber(),
                inventory.getPurchaseDate(),
                inventory.getWarrantyEndDate(),
                inventory.getCurrentStatus(),
                inventory.getCreatedTime(),
                inventory.getRegisteredBy().getId(),
                inventory.getRegisteredBy().getUsername()
        );
    }

    //Entity bulan ve yoksa hata fırlatan özel metot
    private Inventory getExistingInventoryById(Long inventoryId) {
        return inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: " + inventoryId));
    }

    public InventoryResponseDto createInventory(InventoryCreateDto createDto, Long registeredByUserId) {
        //1.Kaydeden kullanıcıyı JWT den gelen ıd yi almak için
        User registeredByUser = userService.getExistingUserById(registeredByUserId);

        //2. DTO'dan entityye dönüştürme
        Inventory inventory = new Inventory();
        inventory.setName(createDto.getName());
        inventory.setSerialNumber(createDto.getSerialNumber());
        inventory.setPurchaseDate(createDto.getPurchaseDate());
        inventory.setWarrantyEndDate(createDto.getWarrantyEndDate());
        inventory.setCurrentStatus(createDto.getCurrentStatus());

        //İlişkiyi ve zamanı ayarlama
        inventory.setRegisteredBy(registeredByUser);
        inventory.setCreatedTime(LocalDateTime.now());

        //3. Kaydetme ve Dto olarak döndürme
        Inventory savedInventory = inventoryRepository.save(inventory);
        return convertToDto(savedInventory);
    }

    //Id ile tekli okuma
    public InventoryResponseDto getInventoryById(Long inventoryId) {
        Inventory inventory = getExistingInventoryById(inventoryId);
        return convertToDto(inventory);
    }

    //Tümünü listeleme
    public List<InventoryResponseDto> getAllInventories() {
        return inventoryRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public InventoryResponseDto updateInventory(Long inventoryId, InventoryUpdateDto updateDto) {
        Inventory existingInventory = getExistingInventoryById(inventoryId);

        //Null kontrolü yaparak saece gelen alanalrı güncelle
        if (updateDto.getName() != null) {
            existingInventory.setName(updateDto.getName());
        }
        if (updateDto.getSerialNumber() != null) {
            existingInventory.setSerialNumber(updateDto.getSerialNumber());
        }
        if (updateDto.getPurchaseDate() != null) {
            existingInventory.setPurchaseDate(updateDto.getPurchaseDate());
        }
        if (updateDto.getWarrantyEndDate() != null) {
            existingInventory.setWarrantyEndDate(updateDto.getWarrantyEndDate());
        }
        if (updateDto.getCurrentStatus() != null) {
            existingInventory.setCurrentStatus(updateDto.getCurrentStatus());
        }
        // Güncelleme zamanını ayarla
        existingInventory.setUpdatedTime(LocalDateTime.now());


        Inventory updatedInventory = inventoryRepository.save(existingInventory);
        return convertToDto(updatedInventory);
    }

    //Delete
    public void deleteInventory(Long inventoryId) {
        Inventory existingInventory = getExistingInventoryById(inventoryId);
        inventoryRepository.delete(existingInventory);
    }
}
