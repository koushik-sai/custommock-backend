package com.custommock.backend.service.impl;

import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.custommock.backend.dto.ComprehensiveResultDto;
import com.custommock.backend.dto.PerformanceSummaryDto;
import com.custommock.backend.dto.SubmitExamRequest;
import com.custommock.backend.entity.ExamAttempt;
import com.custommock.backend.repository.ExamAttemptRepository;
import com.custommock.backend.repository.ExamRepository;
import com.custommock.backend.repository.StudentAnswerRepository;
import com.custommock.backend.service.ResultReviewService;
import com.custommock.backend.service.ReviewService;

@Service
public class ResultReviewServiceImpl implements ResultReviewService {

    private final ExamAttemptRepository attemptRepo;
    private final ExamRepository examRepo;
    private final StudentAnswerRepository answerRepo;
    private final ReviewService reviewService;

    public ResultReviewServiceImpl(
            ExamAttemptRepository attemptRepo,
            ExamRepository examRepo,
            StudentAnswerRepository answerRepo,
            ReviewService reviewService) {
        this.attemptRepo = attemptRepo;
        this.examRepo = examRepo;
        this.answerRepo = answerRepo;
        this.reviewService = reviewService;
    }

    @Override
    public ComprehensiveResultDto getComprehensiveResult(SubmitExamRequest request, Integer totalQuestions) {
        ComprehensiveResultDto result = new ComprehensiveResultDto();

        // Calculate stats
        Integer attempted = request.getAnswers().size();
        Integer unAttempted = totalQuestions - attempted;
        Integer correct = 0;
        Integer wrong = 0;

        // Count correct/wrong
        for (var answer : request.getAnswers()) {
            if (answer.getSelectedAnswer() != null && !answer.getSelectedAnswer().isEmpty()) {
                attempted++;
            }
        }

        // Recount: attempted answers (not null/empty)
        attempted = (int) request.getAnswers().stream()
                .filter(a -> a.getSelectedAnswer() != null && !a.getSelectedAnswer().isEmpty())
                .count();
        unAttempted = totalQuestions - attempted;

        // Get review (correct/wrong status)
        var reviewList = reviewService.reviewExam(request);
        correct = (int) reviewList.stream().filter(r -> "CORRECT".equalsIgnoreCase(r.getStatus())).count();
        wrong = (int) reviewList.stream().filter(r -> "WRONG".equalsIgnoreCase(r.getStatus())).count();

        result.setTotalQuestions(totalQuestions);
        result.setAttempted(attempted);
        result.setUnAttempted(unAttempted);
        result.setCorrect(correct);
        result.setWrong(wrong);

        // Calculate score (basic: 1 point per correct, -0.5 per wrong)
        Double score = correct - (wrong * 0.5);
        result.setScore(score);

        // Calculate percentage
        Double percentage = totalQuestions > 0 ? (correct * 100.0) / totalQuestions : 0;
        result.setPercentage(percentage);

        // Pass/Fail (40% is pass)
        String status = percentage >= 40 ? "PASS" : "FAIL";
        result.setStatus(status);

        // Performance summary
        PerformanceSummaryDto perfSummary = buildPerformanceSummary(percentage, attempted, correct, totalQuestions);
        result.setPerformanceSummary(perfSummary);

        // Detailed review
        result.setDetailedReview(reviewList);

        return result;
    }

    @Override
    public ComprehensiveResultDto getComprehensiveResultByAttempt(Long attemptId, String userEmail) {
        ExamAttempt attempt = attemptRepo.findById(attemptId)
                .orElseThrow(() -> new RuntimeException("Attempt not found"));

        if (!attempt.getUser().getEmail().equals(userEmail)) {
            throw new SecurityException("Not authorized");
        }

        ComprehensiveResultDto result = new ComprehensiveResultDto();

        // Use stored results from attempt
        result.setTotalQuestions(attempt.getExam().getTotalQuestions());
        result.setAttempted(attempt.getCorrectAnswers() + attempt.getWrongAnswers());
        result.setUnAttempted(attempt.getUnAttempted());
        result.setCorrect(attempt.getCorrectAnswers());
        result.setWrong(attempt.getWrongAnswers());
        result.setScore(attempt.getScore());

        Double percentage = attempt.getExam().getTotalQuestions() > 0
                ? (attempt.getCorrectAnswers() * 100.0) / attempt.getExam().getTotalQuestions()
                : 0;
        result.setPercentage(percentage);

        String status = percentage >= 40 ? "PASS" : "FAIL";
        result.setStatus(status);

        // Performance summary
        PerformanceSummaryDto perfSummary = buildPerformanceSummary(percentage, result.getAttempted(),
                attempt.getCorrectAnswers(), attempt.getExam().getTotalQuestions());
        result.setPerformanceSummary(perfSummary);

        // Detailed review from stored answers
        var answers = answerRepo.findByExamAttemptId(attemptId);
        var reviewList = answers.stream().map(sa -> {
            com.custommock.backend.dto.ReviewResponse rev = new com.custommock.backend.dto.ReviewResponse();
            rev.setQuestion(sa.getQuestion().getQuestionText());
            rev.setYourAnswer(sa.getSelectedAnswer());
            rev.setStatus(sa.getSelectedAnswer() != null && !sa.getSelectedAnswer().isEmpty() ? "ANSWERED" : "UNANSWERED");
            return rev;
        }).collect(Collectors.toList());

        result.setDetailedReview(reviewList);

        return result;
    }

    private PerformanceSummaryDto buildPerformanceSummary(Double percentage, Integer attempted, Integer correct,
            Integer total) {
        PerformanceSummaryDto summary = new PerformanceSummaryDto();

        // Performance rating
        String rating;
        if (percentage >= 80) {
            rating = "EXCELLENT";
        } else if (percentage >= 60) {
            rating = "GOOD";
        } else if (percentage >= 40) {
            rating = "AVERAGE";
        } else {
            rating = "POOR";
        }
        summary.setPerformanceRating(rating);

        // Feedback
        String feedback = generateFeedback(rating, percentage);
        summary.setFeedback(feedback);

        // Percentile (simplified: based on percentage)
        Integer percentile = Math.min(100, (int) (percentage / 100.0 * 100));
        summary.setPercentile(percentile);

        // Average time per question (simplified to 0 for now, can be enhanced)
        summary.setAverageTimePerQuestion(0);

        return summary;
    }

    private String generateFeedback(String rating, Double percentage) {
        switch (rating) {
            case "EXCELLENT":
                return "Outstanding performance! You have excellent grasp of the concepts.";
            case "GOOD":
                return "Good performance! You have solid understanding of the topics.";
            case "AVERAGE":
                return "Average performance. Focus on weak areas to improve.";
            case "POOR":
                return "Poor performance. Consider revising the material and practice more.";
            default:
                return "";
        }
    }

}
