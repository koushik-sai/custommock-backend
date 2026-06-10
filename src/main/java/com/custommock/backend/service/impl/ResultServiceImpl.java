package com.custommock.backend.service.impl;


import org.springframework.stereotype.Service;

import com.custommock.backend.dto.ResultResponse;
import com.custommock.backend.dto.SubmitAnswerRequest;
import com.custommock.backend.dto.SubmitExamRequest;
import com.custommock.backend.entity.AnswerKey;
import com.custommock.backend.repository.AnswerKeyRepository;
import com.custommock.backend.service.ResultService;

@Service
public class ResultServiceImpl
        implements ResultService {

    private final AnswerKeyRepository answerKeyRepository;

    public ResultServiceImpl(
            AnswerKeyRepository answerKeyRepository) {

        this.answerKeyRepository =
                answerKeyRepository;
    }

    @Override
    public ResultResponse submitExam(
            SubmitExamRequest request) {

        int correct = 0;
        int wrong = 0;

        for (SubmitAnswerRequest answer
                : request.getAnswers()) {

            AnswerKey key =
                    answerKeyRepository
                            .findById(
                                    answer.getQuestionId())
                            .orElse(null);

            if (key != null) {

                if (key.getCorrectAnswer()
                        .equalsIgnoreCase(
                                answer.getSelectedAnswer())) {

                    correct++;

                } else {

                    wrong++;

                }

            }

        }

        ResultResponse response =
                new ResultResponse();

        response.setCorrect(correct);
        response.setWrong(wrong);
        response.setUnAttempted(0);

        double score =
                correct - (wrong * 0.5);

        response.setScore(score);

        return response;

    }

}