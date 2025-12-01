package com.ramazanayyildiz.EMS.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Long id;
    private String userName;
    private String firstName;
    private String lastName;
    private String role;
    private boolean isActive;
}
