package com.custommock.backend.service;



import java.util.List;

import com.custommock.backend.dto.ExamRequest;
import com.custommock.backend.dto.ExamResponse;

public interface ExamService {

    ExamResponse createExam(ExamRequest request);

    List<ExamResponse> getAllExams();

}
