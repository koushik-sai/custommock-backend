package com.custommock.backend.service;

import com.custommock.backend.dto.AttemptDto;
import com.custommock.backend.dto.StudentAnswerDto;

import java.util.List;

public interface ExamAttemptService {

    AttemptDto startOrResumeAttempt(Long examId, String userEmail);

    AttemptDto getAttempt(Long attemptId, String userEmail);

    AttemptDto autoSaveAnswers(Long attemptId, String userEmail, List<StudentAnswerDto> answers);

    AttemptDto submitAttempt(Long attemptId, String userEmail);

}
