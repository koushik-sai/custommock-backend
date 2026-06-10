package com.custommock.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.custommock.backend.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByExamId(Long examId);
    List<Question> findAllByOrderByIdAsc();

}
