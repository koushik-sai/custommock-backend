package com.custommock.backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.custommock.backend.dto.LoginRequest;
import com.custommock.backend.dto.LoginResponse;
import com.custommock.backend.dto.RegisterRequest;
import com.custommock.backend.dto.ResendOtpRequest;
import com.custommock.backend.dto.VerifyOtpRequest;
import com.custommock.backend.entity.User;
import com.custommock.backend.repository.UserRepository;
import com.custommock.backend.security.JwtUtil;
import com.custommock.backend.service.OtpService;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final OtpService otpService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil, UserRepository userRepository,
                          OtpService otpService, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.otpService = otpService;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

        if (!user.getActive()) {
            throw new RuntimeException("User not activated");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new LoginResponse(token, jwtUtil.getExpirationMs(), user.getRole());
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already registered");
        }

        User user = new User();
        user.setFullName(request.getFullName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("STUDENT");
        user.setActive(false);
        userRepository.save(user);

        otpService.generateAndSend(request.getEmail());

        return ResponseEntity.accepted().body("OTP sent to email");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest request) {
        boolean ok = otpService.verify(request.getEmail(), request.getCode());
        if (!ok) return ResponseEntity.badRequest().body("Invalid or expired OTP");

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        user.setActive(true);
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());
        return ResponseEntity.ok(new LoginResponse(token, jwtUtil.getExpirationMs(), user.getRole()));
    }

    @PostMapping("/resend-otp")
    public ResponseEntity<?> resendOtp(@RequestBody ResendOtpRequest request) {
        otpService.generateAndSend(request.getEmail());
        return ResponseEntity.accepted().body("OTP resent");
    }

}
