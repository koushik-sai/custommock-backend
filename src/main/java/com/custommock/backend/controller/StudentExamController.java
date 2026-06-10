package com.custommock.backend.controller;


import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.custommock.backend.dto.ExamQuestionResponse;
import com.custommock.backend.service.StudentExamService;

@RestController
@RequestMapping("/api/student/exams")
@CrossOrigin("*")
public class StudentExamController {

    private final StudentExamService studentExamService;

    public StudentExamController(
            StudentExamService studentExamService) {

        this.studentExamService =
                studentExamService;
    }

    @GetMapping("/{examId}/start")
    public List<ExamQuestionResponse>
            startExam(
                    @PathVariable Long examId) {

        return studentExamService
                .startExam(examId);

    }

}