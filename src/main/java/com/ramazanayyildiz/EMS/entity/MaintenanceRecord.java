package com.ramazanayyildiz.EMS.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Maintenance_Record")
@AllArgsConstructor
@NoArgsConstructor
@Data
//Not: Bu sınıf bir envanter üzerinde yapılan her bir bakım veya onarım işleminin detayını tutar
public class MaintenanceRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    //Hangi envantere bakım yapıldı
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inventory_id", nullable = false)
    private Inventory inventory;

    //Bakımı kim yaptı
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "technician_user_id", nullable = false)
    private User technician;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(nullable = false, columnDefinition = "NVARCHAR(MAX)")
    private String description;

    //Maliyet, hassas veriler için BigDecimal kullanıldı
    @Column(name = "total_cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalCost = BigDecimal.ZERO;

    @Column(name = "is_completed", nullable = false)
    private Boolean isCompleted = false;

    @Column(name = "created_time", nullable = false, updatable = false)
    private LocalDateTime createdTime;

    @PrePersist
    public void onPrePersist() {
        this.setCreatedTime(LocalDateTime.now());
    }
}
