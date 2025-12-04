package uk.sky.jsdp.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import uk.sky.jsdp.application.dto.AuthDto;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtEncoder jwtEncoder;

    @Value("${app.jwt_expiration}")
    private long jwtExpiration;


    public AuthDto getGeneratedToken(Authentication authentication) {
        Instant expiresAt = Instant.now().plus(jwtExpiration, ChronoUnit.SECONDS);
        return AuthDto.builder().token(jwtEncoder.encode(JwtEncoderParameters.from(JwtClaimsSet.builder()
                .issuer("jsdp-security")
                .issuedAt(Instant.now())
                .expiresAt(expiresAt)
                .subject(authentication.getName())
                .claim("scope", authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(" ")))
                .build())).getTokenValue()).expiresIn(expiresAt.getEpochSecond()).build();
    }
}
