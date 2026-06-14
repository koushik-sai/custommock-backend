package com.custommock.backend.service.impl;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.custommock.backend.dto.AttemptDto;
import com.custommock.backend.dto.StudentAnswerDto;
import com.custommock.backend.entity.ExamAttempt;
import com.custommock.backend.entity.StudentAnswer;
import com.custommock.backend.entity.User;
import com.custommock.backend.repository.ExamAttemptRepository;
import com.custommock.backend.repository.ExamRepository;
import com.custommock.backend.repository.QuestionRepository;
import com.custommock.backend.repository.StudentAnswerRepository;
import com.custommock.backend.repository.UserRepository;
import com.custommock.backend.service.ExamAttemptService;
import com.custommock.backend.service.ResultService;

@Service
public class ExamAttemptServiceImpl implements ExamAttemptService {

    private final ExamAttemptRepository attemptRepo;
    private final UserRepository userRepo;
    private final ExamRepository examRepo;
    private final QuestionRepository questionRepo;
    private final StudentAnswerRepository answerRepo;
    private final ResultService resultService;

    public ExamAttemptServiceImpl(
            ExamAttemptRepository attemptRepo,
            UserRepository userRepo,
            ExamRepository examRepo,
            QuestionRepository questionRepo,
            StudentAnswerRepository answerRepo,
            ResultService resultService) {

        this.attemptRepo = attemptRepo;
        this.userRepo = userRepo;
        this.examRepo = examRepo;
        this.questionRepo = questionRepo;
        this.answerRepo = answerRepo;
        this.resultService = resultService;
    }

    @Override
    @Transactional
    public AttemptDto startOrResumeAttempt(Long examId, String userEmail) {
        User user = userRepo.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

        Optional<ExamAttempt> opt = attemptRepo.findByExam_IdAndUser_IdAndStatus(examId, user.getId(), "IN_PROGRESS");

        ExamAttempt attempt;
        if (opt.isPresent()) {
            attempt = opt.get();
        } else {
            attempt = new ExamAttempt();
            attempt.setExam(examRepo.findById(examId).orElseThrow(() -> new RuntimeException("Exam not found")));
            attempt.setStartTime(LocalDateTime.now());
            Integer duration = attempt.getExam().getDurationMinutes();
            attempt.setEndTime(attempt.getStartTime().plusMinutes(duration == null ? 0 : duration));
            attempt.setStatus("IN_PROGRESS");
            attempt.setUser(user);
            attempt = attemptRepo.save(attempt);
        }

        return buildDtoFromAttempt(attempt);
    }

    @Override
    public AttemptDto getAttempt(Long attemptId, String userEmail) {
        ExamAttempt attempt = attemptRepo.findById(attemptId).orElseThrow();
        if (!attempt.getUser().getEmail().equals(userEmail)) throw new SecurityException("Not owner");
        return buildDtoFromAttempt(attempt);
    }

    @Override
    @Transactional
    public AttemptDto autoSaveAnswers(Long attemptId, String userEmail, List<StudentAnswerDto> answers) {
        ExamAttempt attempt = attemptRepo.findById(attemptId).orElseThrow();
        if (!attempt.getUser().getEmail().equals(userEmail)) throw new SecurityException("Not owner");
        if ("SUBMITTED".equalsIgnoreCase(attempt.getStatus())) throw new IllegalStateException("Attempt already submitted");

        List<StudentAnswer> existing = answerRepo.findByExamAttemptId(attemptId);

        for (StudentAnswerDto dto : answers) {
            StudentAnswer sa = existing.stream()
                    .filter(e -> e.getQuestion().getId().equals(dto.getQuestionId()))
                    .findFirst()
                    .orElse(null);

            if (sa == null) {
                sa = new StudentAnswer();
                sa.setExamAttempt(attempt);
                sa.setQuestion(questionRepo.findById(dto.getQuestionId()).orElseThrow(() -> new RuntimeException("Question not found")));
            }

            sa.setSelectedAnswer(dto.getSelectedAnswer());
            sa.setTimeSpent(dto.getTimeSpent());
            answerRepo.save(sa);
        }

        attempt.setLastSavedAt(LocalDateTime.now());
        attemptRepo.save(attempt);

        return buildDtoFromAttempt(attempt);
    }

    @Override
    @Transactional
    public AttemptDto submitAttempt(Long attemptId, String userEmail) {
        ExamAttempt attempt = attemptRepo.findById(attemptId).orElseThrow();
        if (!attempt.getUser().getEmail().equals(userEmail)) throw new SecurityException("Not owner");
        if ("SUBMITTED".equalsIgnoreCase(attempt.getStatus())) throw new IllegalStateException("Duplicate submission");

        // mark submitted
        attempt.setStatus("SUBMITTED");
        attempt.setSubmittedAt(LocalDateTime.now());
        attempt.setEndTime(attempt.getSubmittedAt());
        attemptRepo.save(attempt);

        // build submit request to reuse ResultService
        com.custommock.backend.dto.SubmitExamRequest req = new com.custommock.backend.dto.SubmitExamRequest();
        req.setStudentName(attempt.getStudentName() == null ? attempt.getUser().getFullName() : attempt.getStudentName());
        req.setExamId(attempt.getExam().getId());

        List<com.custommock.backend.dto.SubmitAnswerRequest> list = answerRepo.findByExamAttemptId(attemptId).stream().map(sa -> {
            com.custommock.backend.dto.SubmitAnswerRequest r = new com.custommock.backend.dto.SubmitAnswerRequest();
            r.setQuestionId(sa.getQuestion().getId());
            r.setSelectedAnswer(sa.getSelectedAnswer());
            return r;
        }).collect(Collectors.toList());

        req.setAnswers(list);

        com.custommock.backend.dto.ResultResponse res = resultService.submitExam(req);

        attempt.setScore(res.getScore());
        attempt.setCorrectAnswers(res.getCorrect());
        attempt.setWrongAnswers(res.getWrong());
        attempt.setUnAttempted(res.getUnAttempted());
        attemptRepo.save(attempt);

        return buildDtoFromAttempt(attempt);
    }

    private AttemptDto buildDtoFromAttempt(ExamAttempt attempt) {
        AttemptDto dto = new AttemptDto();
        dto.setAttemptId(attempt.getId());
        dto.setExamId(attempt.getExam().getId());
        dto.setStartTime(attempt.getStartTime());
        dto.setEndTime(attempt.getEndTime());
        dto.setStatus(attempt.getStatus());

        int remaining = 0;
        if (attempt.getEndTime() != null) {
            Duration d = Duration.between(LocalDateTime.now(), attempt.getEndTime());
            remaining = (int) Math.max(0, d.getSeconds());
        }
        dto.setRemainingSeconds(remaining);

        List<StudentAnswer> answers = answerRepo.findByExamAttemptId(attempt.getId());
        List<com.custommock.backend.dto.StudentAnswerDto> answerDtos = new ArrayList<>();
        for (StudentAnswer sa : answers) {
            com.custommock.backend.dto.StudentAnswerDto a = new com.custommock.backend.dto.StudentAnswerDto();
            a.setQuestionId(sa.getQuestion().getId());
            a.setSelectedAnswer(sa.getSelectedAnswer());
            a.setTimeSpent(sa.getTimeSpent());
            answerDtos.add(a);
        }
        dto.setAnswers(answerDtos);

        return dto;
    }

}
