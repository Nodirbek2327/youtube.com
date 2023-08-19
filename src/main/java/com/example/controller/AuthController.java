package com.example.controller;

import com.example.dto.ApiResponseDTO;
import com.example.dto.AuthDTO;
import com.example.dto.RegistrationDTO;
import com.example.enums.Language;
import com.example.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
   @Autowired
    private AuthService authService;

    @PostMapping(value = {"/login"})
    public ResponseEntity<ApiResponseDTO> login(@RequestBody AuthDTO dto) {
        log.info("login email:  {}"+dto.getEmail());
        return ResponseEntity.ok(authService.login(dto));
    }

    @PostMapping(value = {"/registration"})
    public ResponseEntity<ApiResponseDTO> registration(@RequestBody RegistrationDTO dto,
                                                       @RequestParam(value = "lang", defaultValue = "uz")  Language language) {
        log.info("registration email: "+dto.getEmail());
        return ResponseEntity.ok(authService.registration(dto, language));
    }

    @GetMapping(value = {"/verification/email/{jwt}"})
    public ResponseEntity<ApiResponseDTO> verificationEmail(@PathVariable("jwt") String jwt) {
        log.info("verification email");
        return ResponseEntity.ok(authService.emailVerification(jwt));
    }
}
