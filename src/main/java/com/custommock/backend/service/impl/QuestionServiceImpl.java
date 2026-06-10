package com.custommock.backend.service.impl;



import java.util.ArrayList;
import com.custommock.backend.entity.AnswerKey;
import com.custommock.backend.repository.AnswerKeyRepository;
import java.util.List;

import org.springframework.stereotype.Service;

import com.custommock.backend.dto.QuestionRequest;
import com.custommock.backend.dto.QuestionResponse;
import com.custommock.backend.entity.Exam;
import com.custommock.backend.entity.Question;
import com.custommock.backend.repository.ExamRepository;
import com.custommock.backend.repository.QuestionRepository;
import com.custommock.backend.service.QuestionService;

@Service
public class QuestionServiceImpl implements QuestionService {

    private final QuestionRepository questionRepository;
    private final ExamRepository examRepository;
    private final AnswerKeyRepository answerKeyRepository;

    public QuestionServiceImpl(
            QuestionRepository questionRepository,
            ExamRepository examRepository,
            AnswerKeyRepository answerKeyRepository) {

        this.questionRepository = questionRepository;
        this.examRepository = examRepository;
        this.answerKeyRepository = answerKeyRepository;
    }

    @Override
    public QuestionResponse addQuestion(QuestionRequest request) {

        Exam exam = examRepository.findById(request.getExamId())
                .orElseThrow(() -> new RuntimeException("Exam Not Found"));

        Question question = new Question();

        question.setExam(exam);
        question.setQuestionText(request.getQuestionText());
        question.setOptionA(request.getOptionA());
        question.setOptionB(request.getOptionB());
        question.setOptionC(request.getOptionC());
        question.setOptionD(request.getOptionD());
        question.setSection(request.getSection());
        question.setTopic(request.getTopic());

        Question savedQuestion = questionRepository.save(question);

        return mapToResponse(savedQuestion);

    }

    @Override
    public List<QuestionResponse> getQuestionsByExam(Long examId) {

        List<Question> questions = questionRepository.findByExamId(examId);

        List<QuestionResponse> response = new ArrayList<>();

        for (Question q : questions) {
            response.add(mapToResponse(q));
        }

        return response;
    }

    private QuestionResponse mapToResponse(
            Question question) {

        QuestionResponse response =
                new QuestionResponse();

        response.setId(
                question.getId());

        response.setQuestionText(
                question.getQuestionText());

        response.setOptionA(
                question.getOptionA());

        response.setOptionB(
                question.getOptionB());

        response.setOptionC(
                question.getOptionC());

        response.setOptionD(
                question.getOptionD());

        response.setSection(
                question.getSection());

        response.setTopic(
                question.getTopic());

        answerKeyRepository
                .findByQuestion_Id(
                        question.getId())
                .ifPresent(answerKey ->
                        response.setCorrectAnswer(
                                answerKey.getCorrectAnswer()));

        return response;
    }

}