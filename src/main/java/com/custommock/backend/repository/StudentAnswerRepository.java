package com.custommock.backend.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.custommock.backend.entity.StudentAnswer;

public interface StudentAnswerRepository extends JpaRepository<StudentAnswer, Long> {

    List<StudentAnswer> findByExamAttemptId(Long examAttemptId);

}