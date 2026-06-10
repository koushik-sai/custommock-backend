package com.custommock.backend.service.impl;



import org.springframework.stereotype.Service;

import com.custommock.backend.dto.AnswerKeyRequest;
import com.custommock.backend.dto.AnswerKeyResponse;
import com.custommock.backend.entity.AnswerKey;
import com.custommock.backend.entity.Question;
import com.custommock.backend.repository.AnswerKeyRepository;
import com.custommock.backend.repository.QuestionRepository;
import com.custommock.backend.service.AnswerKeyService;

@Service
public class AnswerKeyServiceImpl
        implements AnswerKeyService {

    private final AnswerKeyRepository answerKeyRepository;
    private final QuestionRepository questionRepository;

    public AnswerKeyServiceImpl(
            AnswerKeyRepository answerKeyRepository,
            QuestionRepository questionRepository) {

        this.answerKeyRepository = answerKeyRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public AnswerKeyResponse addAnswerKey(
            AnswerKeyRequest request) {

        Question question =
                questionRepository.findById(
                        request.getQuestionId())
                        .orElseThrow(
                                () -> new RuntimeException(
                                        "Question Not Found"));

        AnswerKey answerKey = new AnswerKey();

        answerKey.setQuestion(question);
        answerKey.setCorrectAnswer(
                request.getCorrectAnswer());
        answerKey.setExplanation(
                request.getExplanation());

        AnswerKey saved =
                answerKeyRepository.save(answerKey);

        AnswerKeyResponse response =
                new AnswerKeyResponse();

        response.setId(saved.getId());
        response.setCorrectAnswer(
                saved.getCorrectAnswer());
        response.setExplanation(
                saved.getExplanation());

        return response;
    }

}