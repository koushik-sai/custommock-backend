package com.custommock.backend.dto;

public class ExamStatisticsDto {

    private Long examId;
    private String examName;
    private Integer year;
    private String shift;
    private Integer totalAttempts;
    private Integer passedAttempts;
    private Double passRate;
    private Double averageScore;
    private Double averagePercentage;
    private Double highestScore;
    private Double lowestScore;

    public Long getExamId() { return examId; }
    public void setExamId(Long examId) { this.examId = examId; }
    public String getExamName() { return examName; }
    public void setExamName(String examName) { this.examName = examName; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public String getShift() { return shift; }
    public void setShift(String shift) { this.shift = shift; }
    public Integer getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(Integer totalAttempts) { this.totalAttempts = totalAttempts; }
    public Integer getPassedAttempts() { return passedAttempts; }
    public void setPassedAttempts(Integer passedAttempts) { this.passedAttempts = passedAttempts; }
    public Double getPassRate() { return passRate; }
    public void setPassRate(Double passRate) { this.passRate = passRate; }
    public Double getAverageScore() { return averageScore; }
    public void setAverageScore(Double averageScore) { this.averageScore = averageScore; }
    public Double getAveragePercentage() { return averagePercentage; }
    public void setAveragePercentage(Double averagePercentage) { this.averagePercentage = averagePercentage; }
    public Double getHighestScore() { return highestScore; }
    public void setHighestScore(Double highestScore) { this.highestScore = highestScore; }
    public Double getLowestScore() { return lowestScore; }
    public void setLowestScore(Double lowestScore) { this.lowestScore = lowestScore; }

}
