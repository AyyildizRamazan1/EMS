package com.ramazanayyildiz.EMS.repository;

import com.ramazanayyildiz.EMS.entity.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord,Long> {

    // Belirli bir envanterin tüm bakım geçmişini listelemek için
    List<MaintenanceRecord> findByInventoryId(Long inventoryId);

    // Belirli bir teknisyenin yaptığı bakımları listelemek için
    List<MaintenanceRecord> findByTechnicianId(Long technicianId);

    // Tamamlanmış veya devam eden bakımları filtrelemek için
    List<MaintenanceRecord> findByIsCompleted(Boolean isCompleted);

    // Belirli bir envanterin henüz tamamlanmamış (aktif) bakım kaydını bulmak için
    List<MaintenanceRecord> findByInventoryIdAndIsCompletedFalse(Long inventoryId);
}
