package com.custommock.backend.service.impl;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.custommock.backend.dto.ExamRequest;
import com.custommock.backend.dto.ExamResponse;
import com.custommock.backend.entity.Exam;
import com.custommock.backend.repository.ExamRepository;
import com.custommock.backend.service.ExamService;

@Service
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    public ExamServiceImpl(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    @Override
    public ExamResponse createExam(ExamRequest request) {

        Exam exam = new Exam();

        exam.setExamName(request.getExamName());
        exam.setYear(request.getYear());
        exam.setShift(request.getShift());
        exam.setTotalQuestions(request.getTotalQuestions());
        exam.setDurationMinutes(request.getDurationMinutes());
        exam.setNegativeMark(request.getNegativeMark());
        exam.setActive(request.getActive());

        Exam savedExam = examRepository.save(exam);

        return mapToResponse(savedExam);
    }

    @Override
    public List<ExamResponse> getAllExams() {

        return examRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());

    }

    private ExamResponse mapToResponse(Exam exam) {

        ExamResponse response = new ExamResponse();

        response.setId(exam.getId());
        response.setExamName(exam.getExamName());
        response.setYear(exam.getYear());
        response.setShift(exam.getShift());
        response.setTotalQuestions(exam.getTotalQuestions());
        response.setDurationMinutes(exam.getDurationMinutes());
        response.setNegativeMark(exam.getNegativeMark());
        response.setActive(exam.getActive());

        return response;

    }

}
