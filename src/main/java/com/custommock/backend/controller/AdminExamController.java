package com.custommock.backend.controller;



import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.custommock.backend.dto.ExamRequest;
import com.custommock.backend.dto.ExamResponse;
import com.custommock.backend.service.ExamService;

@RestController
@RequestMapping("/api/admin/exams")
@CrossOrigin("*")
public class AdminExamController {

    private final ExamService examService;

    public AdminExamController(ExamService examService) {
        this.examService = examService;
    }

    @PostMapping
    public ExamResponse createExam(@RequestBody ExamRequest request) {
        return examService.createExam(request);
    }

    @GetMapping
    public List<ExamResponse> getAllExams() {
        return examService.getAllExams();
    }

}