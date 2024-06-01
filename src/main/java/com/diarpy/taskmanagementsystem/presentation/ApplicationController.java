package com.diarpy.taskmanagementsystem.presentation;

import com.diarpy.taskmanagementsystem.businessLayer.MyUser;
import com.diarpy.taskmanagementsystem.businessLayer.MyUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.3
 */

@RestController
@RequestMapping("/api")
public class ApplicationController {
    private final MyUserService myUserService;
    private final JwtEncoder jwtEncoder;

    @Autowired
    public ApplicationController(MyUserService myUserService, JwtEncoder jwtEncoder) {
        this.myUserService = myUserService;
        this.jwtEncoder = jwtEncoder;
    }

    @PostMapping(path = "/accounts")
    public ResponseEntity<?> register(@RequestBody @Valid MyUser myUser, Errors errors) {
        return myUserService.register(myUser, errors);
    }

    @PostMapping("/auth/token")
    public Map<String, String> token(Authentication authentication) {
        List<String> authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        JwtClaimsSet claimsSet = JwtClaimsSet.builder()
                .subject(authentication.getName())
                .issuedAt(Instant.now())
                .expiresAt(Instant.now().plus(60, ChronoUnit.SECONDS))
                .claim("scope", authorities)
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claimsSet))
                .getTokenValue();
        return Map.of("token", token);
    }

//    record RegistrationRequest(String email, String password) { }
}