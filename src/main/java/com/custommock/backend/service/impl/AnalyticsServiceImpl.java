package com.custommock.backend.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.custommock.backend.dto.AnalyticsDashboardDto;
import com.custommock.backend.dto.ExamStatisticsDto;
import com.custommock.backend.dto.OverallStatisticsDto;
import com.custommock.backend.dto.UserPerformanceDto;
import com.custommock.backend.entity.Exam;
import com.custommock.backend.entity.ExamAttempt;
import com.custommock.backend.repository.ExamAttemptRepository;
import com.custommock.backend.repository.ExamRepository;
import com.custommock.backend.repository.UserRepository;
import com.custommock.backend.service.AnalyticsService;

@Service
public class AnalyticsServiceImpl implements AnalyticsService {

    private final ExamAttemptRepository attemptRepo;
    private final ExamRepository examRepo;
    private final UserRepository userRepo;

    public AnalyticsServiceImpl(
            ExamAttemptRepository attemptRepo,
            ExamRepository examRepo,
            UserRepository userRepo) {
        this.attemptRepo = attemptRepo;
        this.examRepo = examRepo;
        this.userRepo = userRepo;
    }

    @Override
    public AnalyticsDashboardDto getDashboardAnalytics() {
        AnalyticsDashboardDto dashboard = new AnalyticsDashboardDto();

        // Get all submitted attempts
        List<ExamAttempt> allAttempts = attemptRepo.findAll().stream()
                .filter(a -> "SUBMITTED".equalsIgnoreCase(a.getStatus()))
                .collect(Collectors.toList());

        // Overall statistics
        OverallStatisticsDto overall = calculateOverallStats(allAttempts);
        dashboard.setOverallStatistics(overall);

        // Exam statistics
        List<ExamStatisticsDto> examStats = calculateExamStatistics(allAttempts);
        dashboard.setExamStatistics(examStats);

        // User performance
        List<UserPerformanceDto> userPerfs = calculateUserPerformance(allAttempts);
        
        // Top performers (top 10)
        List<UserPerformanceDto> topPerformers = userPerfs.stream()
                .sorted((a, b) -> Double.compare(b.getAveragePercentage() != null ? b.getAveragePercentage() : 0,
                        a.getAveragePercentage() != null ? a.getAveragePercentage() : 0))
                .limit(10)
                .collect(Collectors.toList());
        dashboard.setTopPerformers(topPerformers);

        // Counts
        dashboard.setTotalAttempts(allAttempts.size());
        dashboard.setTotalUsers((int) allAttempts.stream().map(a -> a.getUser().getId()).distinct().count());
        dashboard.setTotalExams((int) examRepo.count());

        return dashboard;
    }

    private OverallStatisticsDto calculateOverallStats(List<ExamAttempt> attempts) {
        OverallStatisticsDto overall = new OverallStatisticsDto();

        if (attempts.isEmpty()) {
            overall.setAverageScore(0.0);
            overall.setAveragePercentage(0.0);
            overall.setPassRate(0.0);
            overall.setFailRate(0.0);
            return overall;
        }

        Double avgScore = attempts.stream()
                .mapToDouble(a -> a.getScore() != null ? a.getScore() : 0)
                .average()
                .orElse(0);
        overall.setAverageScore(Math.round(avgScore * 100.0) / 100.0);

        // Calculate average percentage
        List<Double> percentages = attempts.stream()
                .map(a -> calculatePercentage(a.getCorrectAnswers(), a.getExam().getTotalQuestions()))
                .collect(Collectors.toList());
        Double avgPercentage = percentages.stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0);
        overall.setAveragePercentage(Math.round(avgPercentage * 100.0) / 100.0);

        // Pass rate (40% is pass)
        Long passCount = percentages.stream().filter(p -> p >= 40).count();
        Double passRate = (passCount * 100.0) / attempts.size();
        overall.setPassRate(Math.round(passRate * 100.0) / 100.0);
        overall.setFailRate(100.0 - overall.getPassRate());

        return overall;
    }

    private List<ExamStatisticsDto> calculateExamStatistics(List<ExamAttempt> attempts) {
        Map<Exam, List<ExamAttempt>> attemptsByExam = attempts.stream()
                .collect(Collectors.groupingBy(ExamAttempt::getExam));

        List<ExamStatisticsDto> stats = new ArrayList<>();

        for (Map.Entry<Exam, List<ExamAttempt>> entry : attemptsByExam.entrySet()) {
            Exam exam = entry.getKey();
            List<ExamAttempt> examAttempts = entry.getValue();

            ExamStatisticsDto stat = new ExamStatisticsDto();
            stat.setExamId(exam.getId());
            stat.setExamName(exam.getExamName());
            stat.setYear(exam.getYear());
            stat.setShift(exam.getShift());

            int totalAttempts = examAttempts.size();
            stat.setTotalAttempts(totalAttempts);

            // Pass rate
            List<Double> percentages = examAttempts.stream()
                    .map(a -> calculatePercentage(a.getCorrectAnswers(), exam.getTotalQuestions()))
                    .collect(Collectors.toList());

            long passCount = percentages.stream().filter(p -> p >= 40).count();
            stat.setPassedAttempts((int) passCount);
            Double passRate = totalAttempts > 0 ? (passCount * 100.0) / totalAttempts : 0;
            stat.setPassRate(Math.round(passRate * 100.0) / 100.0);

            // Average score
            Double avgScore = examAttempts.stream()
                    .mapToDouble(a -> a.getScore() != null ? a.getScore() : 0)
                    .average()
                    .orElse(0);
            stat.setAverageScore(Math.round(avgScore * 100.0) / 100.0);

            // Average percentage
            Double avgPercentage = percentages.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0);
            stat.setAveragePercentage(Math.round(avgPercentage * 100.0) / 100.0);

            // Highest and lowest
            Double highest = percentages.stream().mapToDouble(Double::doubleValue).max().orElse(0);
            Double lowest = percentages.stream().mapToDouble(Double::doubleValue).min().orElse(0);
            stat.setHighestScore(highest);
            stat.setLowestScore(lowest);

            stats.add(stat);
        }

        return stats;
    }

    private List<UserPerformanceDto> calculateUserPerformance(List<ExamAttempt> attempts) {
        Map<Long, List<ExamAttempt>> attemptsByUser = attempts.stream()
                .collect(Collectors.groupingBy(a -> a.getUser().getId()));

        List<UserPerformanceDto> userPerfs = new ArrayList<>();

        for (Map.Entry<Long, List<ExamAttempt>> entry : attemptsByUser.entrySet()) {
            Long userId = entry.getKey();
            List<ExamAttempt> userAttempts = entry.getValue();

            ExamAttempt firstAttempt = userAttempts.get(0);
            UserPerformanceDto perf = new UserPerformanceDto();
            perf.setUserId(userId);
            perf.setUserName(firstAttempt.getUser().getFullName());
            perf.setEmail(firstAttempt.getUser().getEmail());

            // Exams attempted
            long examsAttempted = userAttempts.stream()
                    .map(a -> a.getExam().getId())
                    .distinct()
                    .count();
            perf.setAttemptedExams((int) examsAttempted);
            perf.setTotalAttempts(userAttempts.size());

            // Average score and percentage
            Double avgScore = userAttempts.stream()
                    .mapToDouble(a -> a.getScore() != null ? a.getScore() : 0)
                    .average()
                    .orElse(0);
            perf.setAverageScore(Math.round(avgScore * 100.0) / 100.0);

            List<Double> percentages = userAttempts.stream()
                    .map(a -> calculatePercentage(a.getCorrectAnswers(), a.getExam().getTotalQuestions()))
                    .collect(Collectors.toList());
            Double avgPercentage = percentages.stream()
                    .mapToDouble(Double::doubleValue)
                    .average()
                    .orElse(0);
            perf.setAveragePercentage(Math.round(avgPercentage * 100.0) / 100.0);

            // Passed exams
            long passedCount = percentages.stream().filter(p -> p >= 40).count();
            perf.setPassedExams((int) passedCount);

            userPerfs.add(perf);
        }

        return userPerfs;
    }

    private Double calculatePercentage(Integer correct, Integer total) {
        return total != null && total > 0 ? (correct * 100.0) / total : 0;
    }

}
