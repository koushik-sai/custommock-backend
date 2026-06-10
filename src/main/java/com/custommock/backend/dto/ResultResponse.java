package com.custommock.backend.dto;


public class ResultResponse {

    private Integer correct;

    private Integer wrong;

    private Integer unAttempted;

    private Double score;

    public ResultResponse() {
    }

    public Integer getCorrect() {
        return correct;
    }

    public void setCorrect(Integer correct) {
        this.correct = correct;
    }

    public Integer getWrong() {
        return wrong;
    }

    public void setWrong(Integer wrong) {
        this.wrong = wrong;
    }

    public Integer getUnAttempted() {
        return unAttempted;
    }

    public void setUnAttempted(Integer unAttempted) {
        this.unAttempted = unAttempted;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}