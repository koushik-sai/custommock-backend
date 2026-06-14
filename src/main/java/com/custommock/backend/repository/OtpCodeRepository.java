package com.custommock.backend.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.custommock.backend.entity.OtpCode;

public interface OtpCodeRepository extends JpaRepository<OtpCode, Long> {

    Optional<OtpCode> findFirstByEmailAndUsedFalseOrderByCreatedAtDesc(String email);

    List<OtpCode> findByEmailOrderByCreatedAtDesc(String email);

}
