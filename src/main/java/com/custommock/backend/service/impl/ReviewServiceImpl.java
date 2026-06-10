package com.custommock.backend.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.custommock.backend.dto.ReviewResponse;
import com.custommock.backend.dto.SubmitAnswerRequest;
import com.custommock.backend.dto.SubmitExamRequest;
import com.custommock.backend.entity.AnswerKey;
import com.custommock.backend.entity.Question;
import com.custommock.backend.repository.AnswerKeyRepository;
import com.custommock.backend.repository.QuestionRepository;
import com.custommock.backend.service.ReviewService;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final AnswerKeyRepository answerKeyRepository;
    private final QuestionRepository questionRepository;

    public ReviewServiceImpl(
            AnswerKeyRepository answerKeyRepository,
            QuestionRepository questionRepository) {

        this.answerKeyRepository = answerKeyRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<ReviewResponse> reviewExam(
            SubmitExamRequest request) {

        List<ReviewResponse> result =
                new ArrayList<>();

        for (SubmitAnswerRequest answer :
                request.getAnswers()) {

            Question question =
                    questionRepository.findById(
                            answer.getQuestionId())
                            .orElse(null);

            AnswerKey key =
                    answerKeyRepository
                            .findByQuestion_Id(
                                    answer.getQuestionId())
                            .orElse(null);

            if (question != null &&
                    key != null) {

                ReviewResponse response =
                        new ReviewResponse();

                response.setQuestion(
                        question.getQuestionText());

                response.setYourAnswer(
                        answer.getSelectedAnswer());

                response.setCorrectAnswer(
                        key.getCorrectAnswer());

                response.setExplanation(
                        key.getExplanation());

                if (key.getCorrectAnswer()
                        .equalsIgnoreCase(
                                answer.getSelectedAnswer())) {

                    response.setStatus(
                            "CORRECT");

                } else {

                    response.setStatus(
                            "WRONG");

                }

                result.add(response);
            }
        }

        return result;
    }

}