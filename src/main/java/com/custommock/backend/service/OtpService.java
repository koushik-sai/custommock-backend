package com.custommock.backend.service;

import java.time.LocalDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custommock.backend.entity.OtpCode;
import com.custommock.backend.repository.OtpCodeRepository;

@Service
public class OtpService {

    private final OtpCodeRepository otpRepo;
    private final JavaMailSender mailSender;

    @Value("${otp.expiration.minutes}")
    private long otpExpirationMinutes;

    @Value("${otp.resend.cooldown.seconds}")
    private long resendCooldownSeconds;

    public OtpService(OtpCodeRepository otpRepo, JavaMailSender mailSender) {
        this.otpRepo = otpRepo;
        this.mailSender = mailSender;
    }

    private String generateCode() {
        Random rnd = new Random();
        int number = 100000 + rnd.nextInt(900000);
        return String.valueOf(number);
    }

    @Transactional
    public OtpCode generateAndSend(String email) {
        String code = generateCode();
        OtpCode otp = new OtpCode();
        otp.setEmail(email);
        otp.setCode(code);
        otp.setUsed(false);
        otp.setCreatedAt(LocalDateTime.now());
        otp.setExpiresAt(LocalDateTime.now().plusMinutes(otpExpirationMinutes));

        otpRepo.save(otp);

        // send email
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setFrom("no-reply@custommock.local");
            message.setSubject("Your OTP Code");
            message.setText("Your OTP code is: " + code + " (expires in " + otpExpirationMinutes + " minutes)");
            mailSender.send(message);
        } catch (Exception ex) {
            // logging omitted; keep silent for now
        }

        return otp;
    }

    public boolean verify(String email, String code) {
        return otpRepo.findFirstByEmailAndUsedFalseOrderByCreatedAtDesc(email)
                .map(otp -> {
                    if (otp.getExpiresAt().isBefore(LocalDateTime.now())) return false;
                    if (!otp.getCode().equals(code)) return false;
                    otp.setUsed(true);
                    otpRepo.save(otp);
                    return true;
                }).orElse(false);
    }

}
