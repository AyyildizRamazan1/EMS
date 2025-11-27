package com.ramazanayyildiz.EMS.entity;

import com.ramazanayyildiz.EMS.entity.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name="Users")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column(unique = true, nullable = false, length = 250)
    private String userName;

    @Column(nullable = false,length = 100)
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isActive;
    }
}
