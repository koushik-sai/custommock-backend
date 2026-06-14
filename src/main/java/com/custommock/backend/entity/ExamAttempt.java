package com.custommock.backend.entity;


import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "exam_attempts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExamAttempt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String studentName;

    @ManyToOne
    @JoinColumn(name = "exam_id")
    private Exam exam;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public Exam getExam() {
		return exam;
	}

	public void setExam(Exam exam) {
		this.exam = exam;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public Integer getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(Integer correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public Integer getWrongAnswers() {
		return wrongAnswers;
	}

	public void setWrongAnswers(Integer wrongAnswers) {
		this.wrongAnswers = wrongAnswers;
	}

	public Integer getUnAttempted() {
		return unAttempted;
	}

	public void setUnAttempted(Integer unAttempted) {
		this.unAttempted = unAttempted;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public java.time.LocalDateTime getLastSavedAt() {
		return lastSavedAt;
	}

	public void setLastSavedAt(java.time.LocalDateTime lastSavedAt) {
		this.lastSavedAt = lastSavedAt;
	}

	public java.time.LocalDateTime getSubmittedAt() {
		return submittedAt;
	}

	public void setSubmittedAt(java.time.LocalDateTime submittedAt) {
		this.submittedAt = submittedAt;
	}

	private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double score;

    private Integer correctAnswers;

    private Integer wrongAnswers;

    private Integer unAttempted;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String status; // IN_PROGRESS, SUBMITTED, CANCELLED

    private java.time.LocalDateTime lastSavedAt;

    private java.time.LocalDateTime submittedAt;
}