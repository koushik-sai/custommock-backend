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

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Double score;

    private Integer correctAnswers;

    private Integer wrongAnswers;

    private Integer unAttempted;
}