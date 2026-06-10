package com.custommock.backend.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.custommock.backend.dto.ExamQuestionResponse;
import com.custommock.backend.entity.Question;
import com.custommock.backend.repository.QuestionRepository;
import com.custommock.backend.service.StudentExamService;

@Service
public class StudentExamServiceImpl
        implements StudentExamService {

    private final QuestionRepository questionRepository;

    public StudentExamServiceImpl(
            QuestionRepository questionRepository) {

        this.questionRepository = questionRepository;
    }

    @Override
    public List<ExamQuestionResponse> startExam(
            Long examId) {

        List<Question> questions =
                questionRepository.findByExamId(examId);

        List<ExamQuestionResponse> response =
                new ArrayList<>();

        for (Question q : questions) {

            ExamQuestionResponse dto =
                    new ExamQuestionResponse();

            dto.setId(q.getId());
            dto.setQuestionText(
                    q.getQuestionText());
            dto.setOptionA(q.getOptionA());
            dto.setOptionB(q.getOptionB());
            dto.setOptionC(q.getOptionC());
            dto.setOptionD(q.getOptionD());

            response.add(dto);
        }

        return response;
    }

}