package com.ramazanayyildiz.EMS.controller;

import com.ramazanayyildiz.EMS.dto.InventoryCreateDto;
import com.ramazanayyildiz.EMS.dto.InventoryResponseDto;
import com.ramazanayyildiz.EMS.dto.InventoryUpdateDto;
import com.ramazanayyildiz.EMS.service.InventoryService;
import com.ramazanayyildiz.EMS.service.UserService;
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
@RequestMapping("/api/inventories")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;
    private final UserService userService;

    //Yeni envanter kaydı oluşturmak
    @PostMapping
    @PreAuthorize("hasRole('BOSS')")
    public ResponseEntity<InventoryResponseDto> createInventory(
            @Valid @RequestBody InventoryCreateDto createDto,
            @AuthenticationPrincipal UserDetails userDetails) {

        Long registeredByUSerId = userService.getUserIdByUsername(userDetails.getUsername());

        InventoryResponseDto response = inventoryService.createInventory(createDto, registeredByUSerId);
        return new ResponseEntity<>(response, HttpStatus.CREATED); //201
    }

    //Tüm Kayıtlar
    @GetMapping
    @PreAuthorize("hasAnyRole('BOSS', 'TECHNICIAN')")
    public ResponseEntity<List<InventoryResponseDto>> getAllInventories() {
        List<InventoryResponseDto> inventories = inventoryService.getAllInventories();
        return ResponseEntity.ok(inventories);//200
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('BOSS', 'TECHNICIAN')")
    public ResponseEntity<InventoryResponseDto> getInventoryById(@PathVariable Long id) {
        InventoryResponseDto inventory = inventoryService.getInventoryById(id);
        return ResponseEntity.ok(inventory);//200
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('BOSS')")
    public ResponseEntity<InventoryResponseDto> updateInventory(
            @PathVariable Long id,
            @RequestBody InventoryUpdateDto updateDto){
        InventoryResponseDto updateInventory=inventoryService.updateInventory(id, updateDto);
        return ResponseEntity.ok(updateInventory);//200
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('BOSS')")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id){
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();//204 No Content
    }
}
