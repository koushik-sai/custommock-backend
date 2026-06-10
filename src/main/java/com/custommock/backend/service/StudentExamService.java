package com.custommock.backend.service;


import java.util.List;

import com.custommock.backend.dto.ExamQuestionResponse;

public interface StudentExamService {

    List<ExamQuestionResponse> startExam(
            Long examId);

}