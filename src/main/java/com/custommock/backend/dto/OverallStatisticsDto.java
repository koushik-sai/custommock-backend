package com.custommock.backend.dto;

public class OverallStatisticsDto {

    private Double averageScore;
    private Double averagePercentage;
    private Double passRate;
    private Double failRate;

    public Double getAverageScore() { return averageScore; }
    public void setAverageScore(Double averageScore) { this.averageScore = averageScore; }
    public Double getAveragePercentage() { return averagePercentage; }
    public void setAveragePercentage(Double averagePercentage) { this.averagePercentage = averagePercentage; }
    public Double getPassRate() { return passRate; }
    public void setPassRate(Double passRate) { this.passRate = passRate; }
    public Double getFailRate() { return failRate; }
    public void setFailRate(Double failRate) { this.failRate = failRate; }

}
