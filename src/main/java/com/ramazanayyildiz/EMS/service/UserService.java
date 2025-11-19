package com.ramazanayyildiz.EMS.service;

import com.ramazanayyildiz.EMS.dto.UserCreateDto;
import com.ramazanayyildiz.EMS.entity.User;
import com.ramazanayyildiz.EMS.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor //Lombok: Final alanlar için constructor oluşturur(dependency injection(Autowired))
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserCreateDto dto){

        if(userRepository.findByUserName(dto.getUserName()).isPresent()){
            throw new RuntimeException("Bu kullanıcı adı zaten sistemde mevcut");
        }

        User user=new User();
        user.setUserName(dto.getUserName());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setRole(dto.getRole());

        //Şifrenin bcrypt ile şifrelenmesi
        String hashedPassword=passwordEncoder.encode(dto.getPassword());
        user.setPassword(hashedPassword);

        return userRepository.save(user);
    }

}

