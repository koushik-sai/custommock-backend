package com.custommock.backend.service;

import com.custommock.backend.dto.ComprehensiveResultDto;
import com.custommock.backend.dto.SubmitExamRequest;

public interface ResultReviewService {

    ComprehensiveResultDto getComprehensiveResult(SubmitExamRequest request, Integer totalQuestions);

    ComprehensiveResultDto getComprehensiveResultByAttempt(Long attemptId, String userEmail);

}
