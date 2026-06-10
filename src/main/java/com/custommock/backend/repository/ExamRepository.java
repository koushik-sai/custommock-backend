package com.custommock.backend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.custommock.backend.entity.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long>{

}