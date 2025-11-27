package com.ramazanayyildiz.EMS.security;

import com.ramazanayyildiz.EMS.service.jwt.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        final String autHeader = request.getHeader("Authorization");
        final String jwt;
        final String userName;

        //1.)Token kontrolü
        if (autHeader == null || !autHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        //2.)Token'ı al
        jwt = autHeader.substring(7);
        userName = jwtService.extractUsername(jwt);

        //3.)Kullanıcı Kimlik Doğrulama Kontrolü
        if (userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Kullanıcıyı veritabanına yükle
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);

            //4.) Token'In geçerliliğini kontrol et
            if (jwtService.isTokenValid(jwt, userDetails)) {
                //Güvenlik Token'ı oluştur(Kullanıcı doğrulamıştır)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                //5.)Güvenlik bağlamını güncelle
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
