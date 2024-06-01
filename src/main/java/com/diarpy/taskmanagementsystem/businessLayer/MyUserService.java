package com.diarpy.taskmanagementsystem.businessLayer;

import com.diarpy.taskmanagementsystem.persistance.MyUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.1
 */

@Service
public class MyUserService {
    private final MyUserRepository myUserRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MyUserService(MyUserRepository myUserRepository, PasswordEncoder passwordEncoder) {
        this.myUserRepository = myUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public ResponseEntity<?> register(MyUser myUser, Errors errors) {
        if (myUserRepository.findByEmailIgnoreCase(myUser.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        myUser.setPassword(passwordEncoder.encode(myUser.getPassword()));
        myUser.setAuthority("ROLE_USER");
        myUserRepository.save(myUser);
        return ResponseEntity.ok().build();
    }
}
