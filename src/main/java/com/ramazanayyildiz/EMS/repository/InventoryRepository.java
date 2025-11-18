package com.ramazanayyildiz.EMS.repository;

import com.ramazanayyildiz.EMS.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory,Long> {
}
