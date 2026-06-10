package com.custommock.backend.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "student_answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "exam_attempt_id")
    private ExamAttempt examAttempt;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    private String selectedAnswer;

    private Integer timeSpent;
}