package com.custommock.backend.dto;



public class SubmitAnswerRequest {

    private Long questionId;

    private String selectedAnswer;

    public SubmitAnswerRequest() {
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(String selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

}