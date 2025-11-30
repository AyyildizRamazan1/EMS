package com.ramazanayyildiz.EMS.service;

import com.ramazanayyildiz.EMS.dto.UserCreateDto;
import com.ramazanayyildiz.EMS.dto.UserUpdateDto;
import com.ramazanayyildiz.EMS.entity.User;
import com.ramazanayyildiz.EMS.entity.enums.Role;
import com.ramazanayyildiz.EMS.exception.ResourceNotFoundException;
import com.ramazanayyildiz.EMS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor //Lombok: Final alanlar için constructor oluşturur(dependency injection(Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserCreateDto dto) {

        if (userRepository.findByUserName(dto.getUserName()).isPresent()) {
            throw new RuntimeException("Bu kullanıcı adı zaten sistemde mevcut");
        }

        User user = new User();
        user.setUserName(dto.getUserName());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());

        //Şifrenin bcrypt ile şifrelenmesi
        String hashedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    public User updateUser(Long userId, UserUpdateDto updateDto) {
        User existingUser = getUserById(userId);

        if (updateDto.getFirstName() != null) {
            existingUser.setFirstName(updateDto.getFirstName());
        }
        if (updateDto.getLastName() != null) {
            existingUser.setLastName(updateDto.getLastName());
        }
        if (updateDto.getRole() != null) {
            try {
                Role newRole = Role.valueOf(updateDto.getRole().toUpperCase());
                existingUser.setRole(newRole);
            } catch (IllegalArgumentException e) {
                throw new ResourceNotFoundException("Geçersiz rol tipi: " + updateDto.getRole() + ". Kabul edilenler: BOSS, TECHNICIAN");
            }
        }
        return userRepository.save(existingUser);
    }
}

