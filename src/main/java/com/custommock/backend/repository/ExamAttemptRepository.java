package com.custommock.backend.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.custommock.backend.entity.ExamAttempt;

public interface ExamAttemptRepository extends JpaRepository<ExamAttempt, Long> {

	java.util.Optional<ExamAttempt> findByExam_IdAndUser_IdAndStatus(Long examId, Long userId, String status);

	java.util.List<ExamAttempt> findByUser_Id(Long userId);

}
