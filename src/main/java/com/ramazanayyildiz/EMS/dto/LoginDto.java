package com.ramazanayyildiz.EMS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDto {

    @NotBlank(message = "Kullanıcı adı boş bırakılamaz")
    private String userName;

    @NotBlank(message = "Şifre boş bırakılamaz")
    private String password;
}
