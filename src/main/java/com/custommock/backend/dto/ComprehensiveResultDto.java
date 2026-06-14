package com.custommock.backend.dto;

import java.util.List;

public class ComprehensiveResultDto {

    private Integer totalQuestions;
    private Integer attempted;
    private Integer unAttempted;
    private Integer correct;
    private Integer wrong;
    private Double score;
    private Double percentage;
    private String status; // PASS or FAIL
    private PerformanceSummaryDto performanceSummary;
    private List<ReviewResponse> detailedReview;

    public Integer getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(Integer totalQuestions) { this.totalQuestions = totalQuestions; }
    public Integer getAttempted() { return attempted; }
    public void setAttempted(Integer attempted) { this.attempted = attempted; }
    public Integer getUnAttempted() { return unAttempted; }
    public void setUnAttempted(Integer unAttempted) { this.unAttempted = unAttempted; }
    public Integer getCorrect() { return correct; }
    public void setCorrect(Integer correct) { this.correct = correct; }
    public Integer getWrong() { return wrong; }
    public void setWrong(Integer wrong) { this.wrong = wrong; }
    public Double getScore() { return score; }
    public void setScore(Double score) { this.score = score; }
    public Double getPercentage() { return percentage; }
    public void setPercentage(Double percentage) { this.percentage = percentage; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public PerformanceSummaryDto getPerformanceSummary() { return performanceSummary; }
    public void setPerformanceSummary(PerformanceSummaryDto performanceSummary) { this.performanceSummary = performanceSummary; }
    public List<ReviewResponse> getDetailedReview() { return detailedReview; }
    public void setDetailedReview(List<ReviewResponse> detailedReview) { this.detailedReview = detailedReview; }

}
