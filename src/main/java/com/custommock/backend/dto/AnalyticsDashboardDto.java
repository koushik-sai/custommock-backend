package com.custommock.backend.dto;

import java.util.List;

public class AnalyticsDashboardDto {

    private OverallStatisticsDto overallStatistics;
    private List<ExamStatisticsDto> examStatistics;
    private List<UserPerformanceDto> topPerformers;
    private Integer totalUsers;
    private Integer totalExams;
    private Integer totalAttempts;

    public OverallStatisticsDto getOverallStatistics() { return overallStatistics; }
    public void setOverallStatistics(OverallStatisticsDto overallStatistics) { this.overallStatistics = overallStatistics; }
    public List<ExamStatisticsDto> getExamStatistics() { return examStatistics; }
    public void setExamStatistics(List<ExamStatisticsDto> examStatistics) { this.examStatistics = examStatistics; }
    public List<UserPerformanceDto> getTopPerformers() { return topPerformers; }
    public void setTopPerformers(List<UserPerformanceDto> topPerformers) { this.topPerformers = topPerformers; }
    public Integer getTotalUsers() { return totalUsers; }
    public void setTotalUsers(Integer totalUsers) { this.totalUsers = totalUsers; }
    public Integer getTotalExams() { return totalExams; }
    public void setTotalExams(Integer totalExams) { this.totalExams = totalExams; }
    public Integer getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(Integer totalAttempts) { this.totalAttempts = totalAttempts; }

}
