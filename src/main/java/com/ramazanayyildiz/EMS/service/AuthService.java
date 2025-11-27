package com.ramazanayyildiz.EMS.service;

import com.ramazanayyildiz.EMS.dto.LoginDto;
import com.ramazanayyildiz.EMS.service.jwt.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;
    private final JwtService jwtService;

    public String login(LoginDto dto){
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUserName(),
                        dto.getPassword()
                )
        );

        UserDetails userDetails=userDetailsService.loadUserByUsername(dto.getUserName());

        return jwtService.generateToken(userDetails);
    }

}
