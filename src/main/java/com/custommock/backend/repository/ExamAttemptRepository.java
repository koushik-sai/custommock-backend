package com.custommock.backend.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.custommock.backend.entity.ExamAttempt;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {

}
