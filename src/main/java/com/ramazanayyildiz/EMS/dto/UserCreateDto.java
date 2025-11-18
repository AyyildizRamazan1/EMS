package com.ramazanayyildiz.EMS.dto;

import com.ramazanayyildiz.EMS.entity.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserCreateDto {

    @NotBlank(message = "Kullanıcı adı zorunludur")
    @Size(min=3,max=50,message = "Kullanıcı adı 3 ile 50 karakter arasında olmalıdır.")
    private String userName;

    @NotBlank(message = "Şifre zorunludur")
    private String password;

    @NotBlank(message = "Ad zorunludur")
    private String firstName;

    @NotBlank(message = "Soyad zorunludur")
    private String lastName;

    @NotNull(message = "Rol bilgisi zorunludur")
    private Role role;
}
