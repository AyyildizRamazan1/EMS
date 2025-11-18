package com.ramazanayyildiz.EMS.repository;

import com.ramazanayyildiz.EMS.entity.MaintenanceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceRecordRepository extends JpaRepository<MaintenanceRecord,Long> {
}
