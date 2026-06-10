package com.custommock.backend.dto;


import java.util.List;

public class SubmitExamRequest {

    private String studentName;

    private Long examId;

    private List<SubmitAnswerRequest> answers;

    public SubmitExamRequest() {
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getExamId() {
        return examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
    }

    public List<SubmitAnswerRequest> getAnswers() {
        return answers;
    }

    public void setAnswers(List<SubmitAnswerRequest> answers) {
        this.answers = answers;
    }

}