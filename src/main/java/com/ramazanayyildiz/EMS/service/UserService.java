package com.ramazanayyildiz.EMS.service;

import com.ramazanayyildiz.EMS.dto.UserCreateDto;
import com.ramazanayyildiz.EMS.dto.UserResponseDto;
import com.ramazanayyildiz.EMS.dto.UserUpdateDto;
import com.ramazanayyildiz.EMS.entity.User;
import com.ramazanayyildiz.EMS.entity.enums.Role;
import com.ramazanayyildiz.EMS.exception.ResourceNotFoundException;
import com.ramazanayyildiz.EMS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    private UserResponseDto converToDto(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getRole().name(),
                user.isEnabled()
        );
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(this::converToDto)//Her User'ı DTO'ya dönüştür
                .collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with : " + userId));
        return converToDto(user);
    }

    public User getExistingUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with: " + userId));
    }

    public UserResponseDto updateUser(Long userId, UserUpdateDto updateDto) {
        User existingUser = getExistingUserById(userId);

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
        User updatedUser = userRepository.save(existingUser);
        return converToDto(updatedUser);
    }

    public void deleteUser(Long userId){
        User existingUser=getExistingUserById(userId);
        userRepository.deleteById(existingUser.getId());
    }

    public Long getUserIdByUsername(String username){
        User user= userRepository.findByUserName(username)
                .orElseThrow(()-> new ResourceNotFoundException("Kullanıcı adı bulunamadı: " + username));
        return user.getId();
    }

}

