package com.custommock.backend.dto;

public class ReviewResponse {

    private String question;
    private String yourAnswer;
    private String correctAnswer;
    private String explanation;
    private String status;

    public ReviewResponse() {

    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getYourAnswer() {
        return yourAnswer;
    }

    public void setYourAnswer(String yourAnswer) {
        this.yourAnswer = yourAnswer;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String getExplanation() {
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}