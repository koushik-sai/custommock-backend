package com.custommock.backend.service.impl;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.custommock.backend.entity.Exam;
import com.custommock.backend.entity.Question;
import com.custommock.backend.repository.ExamRepository;
import com.custommock.backend.repository.QuestionRepository;
import com.custommock.backend.service.PdfParserService;
import com.custommock.backend.entity.AnswerKey;
import com.custommock.backend.repository.AnswerKeyRepository;

@Service
public class PdfParserServiceImpl
        implements PdfParserService {

    private final QuestionRepository questionRepository;

    private final ExamRepository examRepository;

    private final AnswerKeyRepository answerKeyRepository;

    public PdfParserServiceImpl(
            QuestionRepository questionRepository,
            ExamRepository examRepository,
            AnswerKeyRepository answerKeyRepository) {

        this.questionRepository =
                questionRepository;

        this.examRepository =
                examRepository;

        this.answerKeyRepository =
                answerKeyRepository;
    }

    @Override
    public void createExamFromText(
            String text) {

        Exam exam = new Exam();

        exam.setExamName(
                "SSC Uploaded Paper");

        exam.setYear(2025);

        exam.setShift(
                "Morning");

        exam.setDurationMinutes(
                60);

        exam.setNegativeMark(
                0.50);

        exam.setActive(true);

        exam.setTotalQuestions(0);

        exam =
                examRepository.save(exam);

        int questionCount = 0;

        Pattern pattern =
                Pattern.compile(
                        "Q(\\d+)\\s+(.*?)(\\(A\\).*?\\(D\\).*?)(?=Q\\d+|ANSWER KEY|Answer Key|$)",
                        Pattern.DOTALL);

        Matcher matcher =
                pattern.matcher(text);

        while (matcher.find()) {

            try {

                String block =
                        matcher.group();

                block =
                        block.replace("á", " ")
                             .replace("\r", " ")
                             .replace("\n", " ")
                             .trim();

                int aIndex =
                        block.indexOf("(A)");

                int bIndex =
                        block.indexOf("(B)");

                int cIndex =
                        block.indexOf("(C)");

                int dIndex =
                        block.indexOf("(D)");

                if (aIndex < 0 ||
                    bIndex < 0 ||
                    cIndex < 0 ||
                    dIndex < 0) {

                    System.out.println(
                            "SKIPPED QUESTION");

                    continue;
                }

                if (!(aIndex < bIndex &&
                      bIndex < cIndex &&
                      cIndex < dIndex)) {

                    System.out.println(
                            "INVALID OPTION ORDER");

                    continue;
                }

                String questionText =
                        block.substring(
                                0,
                                aIndex)
                                .trim();

                String optionA =
                        block.substring(
                                aIndex + 3,
                                bIndex)
                                .trim();

                String optionB =
                        block.substring(
                                bIndex + 3,
                                cIndex)
                                .trim();

                String optionC =
                        block.substring(
                                cIndex + 3,
                                dIndex)
                                .trim();

                String optionD =
                        block.substring(
                                dIndex + 3)
                                .trim();

                Question question =
                        new Question();

                question.setExam(exam);

                question.setQuestionText(
                        questionText);

                question.setOptionA(
                        optionA);

                question.setOptionB(
                        optionB);

                question.setOptionC(
                        optionC);

                question.setOptionD(
                        optionD);

                questionRepository.save(
                        question);

                questionCount++;

                System.out.println(
                        "QUESTION SAVED : "
                        + questionCount);

            } catch (Exception ex) {

                System.out.println(
                        "FAILED QUESTION");

                ex.printStackTrace();
            }
        }

        exam.setTotalQuestions(
                questionCount);

        examRepository.save(
                exam);
        saveAnswerKeys(text);

        System.out.println(
                "TOTAL QUESTIONS SAVED : "
                + questionCount);
        System.out.println("========== LAST 5000 CHARS ==========");
        System.out.println(
            text.substring(
                Math.max(0, text.length() - 5000)
            )
        );
    }
    
    private void saveAnswerKeys(
            String text) {

        int answerKeyStart =
                text.indexOf(
                        "Answer Key");

        if (answerKeyStart == -1) {

            System.out.println(
                    "ANSWER KEY SECTION NOT FOUND");

            return;
        }

        String answerKeyText =
                text.substring(
                        answerKeyStart);
        

        System.out.println(
                "===== ANSWER KEY SECTION =====");

        System.out.println(
                answerKeyText.substring(
                        0,
                        Math.min(
                                3000,
                                answerKeyText.length())));

        List<Question> questions =
                questionRepository
                        .findAllByOrderByIdAsc();
        answerKeyText =
                answerKeyText.replace(
                        '\u00A0',
                        ' ');

        Pattern pattern =
                Pattern.compile(
                        "Q\\s*(\\d+)\\s*[\\u00A0\\s]*\\(([A-D])\\)");

        Matcher matcher =
                pattern.matcher(
                        answerKeyText);
        
        System.out.println(
                "MATCH FOUND = "
                + matcher.find());

        matcher.reset();

        while (matcher.find()) {

            try {

                int questionNumber =
                        Integer.parseInt(
                                matcher.group(1));

                String answer =
                        matcher.group(2);

                if (questionNumber <= 0 ||
                    questionNumber > questions.size()) {

                    continue;
                }

                Question question =
                        questions.get(
                                questionNumber - 1);

                if (answerKeyRepository
                        .findByQuestion_Id(
                                question.getId())
                        .isPresent()) {

                    continue;
                }

                AnswerKey key =
                        new AnswerKey();

                key.setQuestion(question);

                key.setCorrectAnswer(
                        answer);

                key.setExplanation(
                        "Imported from PDF");

                answerKeyRepository.save(
                        key);

                System.out.println(
                        "ANSWER KEY SAVED : Q"
                        + questionNumber
                        + " -> "
                        + answer);

            } catch (Exception ex) {

                System.out.println(
                        "FAILED ANSWER KEY");

                ex.printStackTrace();
            }
        }
    }

}