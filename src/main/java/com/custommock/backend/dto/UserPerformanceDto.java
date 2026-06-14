package com.custommock.backend.dto;

public class UserPerformanceDto {

    private Long userId;
    private String userName;
    private String email;
    private Integer attemptedExams;
    private Integer passedExams;
    private Double averageScore;
    private Double averagePercentage;
    private Integer totalAttempts;

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Integer getAttemptedExams() { return attemptedExams; }
    public void setAttemptedExams(Integer attemptedExams) { this.attemptedExams = attemptedExams; }
    public Integer getPassedExams() { return passedExams; }
    public void setPassedExams(Integer passedExams) { this.passedExams = passedExams; }
    public Double getAverageScore() { return averageScore; }
    public void setAverageScore(Double averageScore) { this.averageScore = averageScore; }
    public Double getAveragePercentage() { return averagePercentage; }
    public void setAveragePercentage(Double averagePercentage) { this.averagePercentage = averagePercentage; }
    public Integer getTotalAttempts() { return totalAttempts; }
    public void setTotalAttempts(Integer totalAttempts) { this.totalAttempts = totalAttempts; }

}
