package com.theodo.albeniz.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.theodo.albeniz.dto.SignUpForm;
import com.theodo.albeniz.model.UserEntity;
import com.theodo.albeniz.services.UserSignUpService;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/users")
@AllArgsConstructor()
public class UserController {
    private final UserSignUpService userSignUpService;

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.OK)
    private ResponseEntity<UserEntity> userSignup(@RequestBody @Valid SignUpForm signUpForm) {
        return ResponseEntity.ok(userSignUpService.signUp(signUpForm));
    }
}
