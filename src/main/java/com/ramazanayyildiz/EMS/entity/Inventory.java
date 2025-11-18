package com.ramazanayyildiz.EMS.entity;

import com.ramazanayyildiz.EMS.entity.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "Inventory")
@AllArgsConstructor
@NoArgsConstructor
@Data
//Not: Bu sınıf takip edeceğimiz her bi demirbaşı temsil eder
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(name = "serial_number", unique = true, nullable = false, length = 100)
    private String serialNumber;

    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate; //satın alma tarihi

    @Column(name = "warranty_end_date")
    private LocalDate warrantyEndDate; // garanti bitiş tarihi

    //InventoryStatus Enum ilişkilendirme
    @Enumerated(EnumType.STRING)
    @Column(name = "current_status", nullable = false, length = 20)
    private InventoryStatus currentStatus;

    //Kim kaydetti?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "registered_by_user_id", nullable = false)
    private User registeredBy;

    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    public void onPrePersist() {
        this.setCreatedTime(LocalDateTime.now());
    }
}
