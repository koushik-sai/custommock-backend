package com.custommock.backend.service;



import java.util.List;

import com.custommock.backend.dto.QuestionRequest;
import com.custommock.backend.dto.QuestionResponse;

public interface QuestionService {

    QuestionResponse addQuestion(QuestionRequest request);

    List<QuestionResponse> getQuestionsByExam(Long examId);

}