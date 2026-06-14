package com.custommock.backend.dto;

public class PerformanceSummaryDto {

    private String performanceRating; // EXCELLENT, GOOD, AVERAGE, POOR
    private String feedback;
    private Integer percentile;
    private Integer averageTimePerQuestion;

    public String getPerformanceRating() { return performanceRating; }
    public void setPerformanceRating(String performanceRating) { this.performanceRating = performanceRating; }
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    public Integer getPercentile() { return percentile; }
    public void setPercentile(Integer percentile) { this.percentile = percentile; }
    public Integer getAverageTimePerQuestion() { return averageTimePerQuestion; }
    public void setAverageTimePerQuestion(Integer averageTimePerQuestion) { this.averageTimePerQuestion = averageTimePerQuestion; }

}
