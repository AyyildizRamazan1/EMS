package com.ramazanayyildiz.EMS.service;

import com.ramazanayyildiz.EMS.dto.MaintenanceRecordCreateDto;
import com.ramazanayyildiz.EMS.dto.MaintenanceRecordResponseDto;
import com.ramazanayyildiz.EMS.entity.Inventory;
import com.ramazanayyildiz.EMS.entity.MaintenanceRecord;
import com.ramazanayyildiz.EMS.entity.User;
import com.ramazanayyildiz.EMS.entity.enums.InventoryStatus;
import com.ramazanayyildiz.EMS.exception.ResourceNotFoundException;
import com.ramazanayyildiz.EMS.repository.InventoryRepository;
import com.ramazanayyildiz.EMS.repository.MaintenanceRecordRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceRecordService {

    private final MaintenanceRecordRepository maintenanceRecordRepository;
    private final InventoryRepository inventoryRepository;
    private final UserService userService;

    @Transactional
    public MaintenanceRecordResponseDto createRecord(MaintenanceRecordCreateDto dto, String technicianUsername) {
        //1.) envanteri bulma
        Inventory inventory = inventoryRepository.findById(dto.getInventoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Envanter bulunamadı"));

        //2.) oturum açan kullanıcı bulma
        User technician = userService.getExistingUserById(userService.getUserIdByUsername(technicianUsername));

        //3.) Envanter durum güncelleme
        inventory.setCurrentStatus(InventoryStatus.IN_MAINTENANCE);
        inventoryRepository.save(inventory);

        //4.) kaydı oluşturma
        MaintenanceRecord record = new MaintenanceRecord();
        record.setInventory(inventory);
        record.setTechnician(technician);
        record.setStartDate(dto.getStartDate() != null ? dto.getStartDate() : LocalDateTime.now());
        record.setDescription(dto.getDescription());
        record.setTotalCost(dto.getTotalCost());
        record.setIsCompleted(false);

        MaintenanceRecord saved = maintenanceRecordRepository.save(record);
        return convertToDto(saved);
    }


    @Transactional
    public MaintenanceRecordResponseDto completeMaintenance(Long recordId) {
        MaintenanceRecord record = maintenanceRecordRepository.findById(recordId)
                .orElseThrow(() -> new ResourceNotFoundException("Bakım kaydı bulunamadı"));

        record.setIsCompleted(true);
        record.setEndDate(LocalDateTime.now());

        record.getInventory().setCurrentStatus(InventoryStatus.IN_USE);

        MaintenanceRecord saved=maintenanceRecordRepository.save(record);
        return convertToDto(saved);
    }

    public List<MaintenanceRecordResponseDto> getAllRecords(){
        return maintenanceRecordRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MaintenanceRecord getExistingRecordById(Long id){
        return maintenanceRecordRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Bakım kaydı bulunamadı. ID: "+ id));
    }

    public MaintenanceRecordResponseDto getRecordById(Long id){
        MaintenanceRecord record=getExistingRecordById(id);
        return convertToDto(record);
    }

    private MaintenanceRecordResponseDto convertToDto(MaintenanceRecord entity) {
        MaintenanceRecordResponseDto dto = new MaintenanceRecordResponseDto();
        dto.setId(entity.getId());
        dto.setInventoryId(entity.getInventory().getId());
        dto.setInventoryName(entity.getInventory().getName());
        dto.setTechnicianUsername(entity.getTechnician().getUsername());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDescription(entity.getDescription());
        dto.setTotalCost(entity.getTotalCost());
        dto.setIsCompleted(entity.getIsCompleted());
        dto.setCreatedTime(entity.getCreatedTime());
        return dto;
    }
}
