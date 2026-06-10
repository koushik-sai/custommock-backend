package com.custommock.backend.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table(name = "exams")
@Entity
public class Exam {
	public Exam(){
		
	}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String examName;

    private Integer year;

    private String shift;

    private Integer totalQuestions;

    private Integer durationMinutes;

    private Double negativeMark;
    public Exam build() {
        Exam exam = new Exam();
        exam.setId(id);
        exam.setExamName(examName);
        exam.setYear(year);
        exam.setShift(shift);
        exam.setTotalQuestions(totalQuestions);
        exam.setDurationMinutes(durationMinutes);
        exam.setNegativeMark(negativeMark);
        exam.setActive(active);
        return exam;
    }

    

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getShift() {
		return shift;
	}

	public void setShift(String shift) {
		this.shift = shift;
	}

	public Integer getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(Integer totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public Integer getDurationMinutes() {
		return durationMinutes;
	}

	public void setDurationMinutes(Integer durationMinutes) {
		this.durationMinutes = durationMinutes;
	}

	public Double getNegativeMark() {
		return negativeMark;
	}

	public void setNegativeMark(Double negativeMark) {
		this.negativeMark = negativeMark;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	private Boolean active;
}
