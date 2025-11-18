package com.ramazanayyildiz.EMS.entity;

import com.ramazanayyildiz.EMS.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name="Users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String userName;

    @Column(nullable = false,length = 255)
    private String password;

    @Column(name = "first_name", nullable = false,length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false,length = 50)
    private String lastName;

    @Column(nullable = false,length = 25)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "is_active",nullable = false)
    private Boolean isActive=true;

    @Column(name = "created_time",updatable = false)
    private LocalDateTime createdTime;

    @PrePersist
    public void onPrePersist(){
        this.setCreatedTime(LocalDateTime.now());
    }

}
