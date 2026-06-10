package com.custommock.backend.controller;


import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.custommock.backend.dto.QuestionRequest;
import com.custommock.backend.dto.QuestionResponse;
import com.custommock.backend.service.QuestionService;

@RestController
@RequestMapping("/api/admin/questions")
@CrossOrigin("*")
public class AdminQuestionController {

    private final QuestionService questionService;

    public AdminQuestionController(
            QuestionService questionService) {

        this.questionService = questionService;
    }

    @PostMapping
    public QuestionResponse addQuestion(
            @RequestBody QuestionRequest request) {

        return questionService.addQuestion(request);
    }

    @GetMapping("/{examId}")
    public List<QuestionResponse> getQuestions(
            @PathVariable Long examId) {

        return questionService.getQuestionsByExam(examId);
    }

}