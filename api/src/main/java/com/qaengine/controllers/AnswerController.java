package com.qaengine.controllers;

import com.qaengine.database.AnswerRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Answer;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.AnswerInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AnswerController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionController questionController;

    @CrossOrigin()
    @PostMapping("answers")
    protected Answer addAnswer(
            @RequestBody AnswerInput answerInput
    ) {
        Question question = questionController.getQuestion(answerInput.getQuestionId());
        Answer answer = new Answer();
        HelperFunctions.copyProperties(answer, answerInput);
        answer = answerRepository.save(answer);
        question.getAnswers().add(answer);
        questionRepository.save(question);
        return answer;
    }
}
