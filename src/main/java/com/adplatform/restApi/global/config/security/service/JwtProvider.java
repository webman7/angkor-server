package com.adplatform.restApi.global.config.security.service;

import com.adplatform.restApi.domain.user.domain.User;
import com.adplatform.restApi.global.config.security.dto.TokenDto;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.prefix}")
    private String TOKEN_PREFIX;

    private final Aes256Service aes256Service;
    private final UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    public String createToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        return String.format("%s%s", TOKEN_PREFIX, Jwts.builder()
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(Timestamp.valueOf(LocalDateTime.now().plusHours(1)))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact());
    }

    public TokenDto createTokenDto(User user) {
        return TokenDto.create(this.aes256Service, user, this.createToken(user.getLoginId()), null);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = this.parseClaims(token);
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(claims.getSubject());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private Claims parseClaims(String token) {
        try {
            token = this.extractToken(token);
            return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public String resolveToken(HttpServletRequest request) {
        return request.getHeader(HttpHeaders.AUTHORIZATION);
    }

    public boolean validateToken(String token) {
        token = this.extractToken(token);
        Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token);
        return true;
    }

    private String extractToken(String token) {
        if (!token.startsWith(TOKEN_PREFIX)) throw new IllegalArgumentException();
        return token.substring(TOKEN_PREFIX.length());
    }
}
