package com.custommock.backend.dto;

public class ExamResponse {

    public ExamResponse(Long id, String examName, Integer year, String shift, Integer totalQuestions,
			Integer durationMinutes, Double negativeMark, Boolean active) {
		super();
		this.id = id;
		this.examName = examName;
		this.year = year;
		this.shift = shift;
		this.totalQuestions = totalQuestions;
		this.durationMinutes = durationMinutes;
		this.negativeMark = negativeMark;
		this.active = active;
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

	private Long id;

    private String examName;

    private Integer year;

    private String shift;

    private Integer totalQuestions;

    private Integer durationMinutes;

    private Double negativeMark;

    private Boolean active;

	public ExamResponse() {
		// TODO Auto-generated constructor stub
	}

	

}