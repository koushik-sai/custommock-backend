package com.custommock.backend.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.custommock.backend.dto.AttemptDto;
import com.custommock.backend.dto.ComprehensiveResultDto;
import com.custommock.backend.dto.StudentAnswerDto;
import com.custommock.backend.service.ExamAttemptService;
import com.custommock.backend.service.ResultReviewService;

@RestController
@RequestMapping("/api/student/exams")
@CrossOrigin("*")
public class ExamAttemptController {

    private final ExamAttemptService attemptService;
    private final ResultReviewService resultReviewService;

    public ExamAttemptController(ExamAttemptService attemptService, ResultReviewService resultReviewService) {
        this.attemptService = attemptService;
        this.resultReviewService = resultReviewService;
    }

    @PostMapping("/{examId}/attempt")
    public ResponseEntity<AttemptDto> startAttempt(@PathVariable Long examId, Principal principal) {
        AttemptDto dto = attemptService.startOrResumeAttempt(examId, principal.getName());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/attempts/{attemptId}")
    public ResponseEntity<AttemptDto> getAttempt(@PathVariable Long attemptId, Principal principal) {
        AttemptDto dto = attemptService.getAttempt(attemptId, principal.getName());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/attempts/{attemptId}/autosave")
    public ResponseEntity<AttemptDto> autosave(@PathVariable Long attemptId,
                                               @RequestBody List<StudentAnswerDto> answers,
                                               Principal principal) {
        AttemptDto dto = attemptService.autoSaveAnswers(attemptId, principal.getName(), answers);
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/attempts/{attemptId}/submit")
    public ResponseEntity<AttemptDto> submit(@PathVariable Long attemptId, Principal principal) {
        AttemptDto dto = attemptService.submitAttempt(attemptId, principal.getName());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/attempts/{attemptId}/result")
    public ResponseEntity<ComprehensiveResultDto> getResult(@PathVariable Long attemptId, Principal principal) {
        ComprehensiveResultDto result = resultReviewService.getComprehensiveResultByAttempt(attemptId, principal.getName());
        return ResponseEntity.ok(result);
    }

}
