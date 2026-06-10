package com.custommock.backend.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.custommock.backend.entity.AnswerKey;

public interface AnswerKeyRepository
extends JpaRepository<AnswerKey, Long>{

Optional<AnswerKey> findByQuestion_Id(
    Long questionId);

}
