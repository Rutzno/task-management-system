package com.diarpy.taskmanagementsystem;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mack_TB
 * @since 18/05/2024
 * @version 1.0.1
 */

@RestController
@RequestMapping("/api")
public class ApplicationController {
    private final MyUserService myUserService;

    @Autowired
    public ApplicationController(MyUserService myUserService) {
        this.myUserService = myUserService;
    }

    @PostMapping(path = "/accounts")
    public ResponseEntity<?> register(@RequestBody @Valid MyUser myUser, Errors errors) {
        return myUserService.register(myUser, errors);
    }

    @GetMapping(path = "/tasks")
    @ResponseStatus(code = HttpStatus.OK)
    public void getTasks() {

    }

//    record RegistrationRequest(String email, String password) { }
}
