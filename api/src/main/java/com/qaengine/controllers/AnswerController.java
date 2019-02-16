package com.qaengine.controllers;

import com.qaengine.database.AnswerRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Answer;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.AnswerInput;
import com.qaengine.models.inputs.QuestionInput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class AnswerController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    QuestionController questionController;

    @CrossOrigin()
    @PostMapping("questions/{questionId}/answers")
    protected Answer addAnswer(
            @PathVariable Long questionId,
            @RequestBody AnswerInput answerInput
    ) {
        Question question = questionController.getQuestion(questionId);
        Answer answer = new Answer();
        HelperFunctions.copyProperties(answer, answerInput);
        answer = answerRepository.save(answer);
        question.getAnswers().add(answer);
        questionRepository.save(question);
        return answer;
    }

    @GetMapping("questions/{questionId}/answers")
    @CrossOrigin()
    public List<Answer> listAnswers(@PathVariable Long questionId) {
        Question question = questionController.getQuestion(questionId);
        return question.getAnswers();
    }

    @GetMapping("questions2")
    @CrossOrigin()
    public List<Answer> listAnswers2() {
        return answerRepository.findAll();
    }


    @CrossOrigin()
    @GetMapping("answers/{id}")
    protected Answer getAnswer(@PathVariable Long id) {
        Optional<Answer> answer = answerRepository.findById(id);
        if (answer.isPresent()) {
            return answer.get();
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @CrossOrigin()
    @DeleteMapping("answers/{id}")
    protected Long deleteAnswer(@PathVariable Long id) {
        try {
            answerRepository.deleteById(id);
            return id;
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    @CrossOrigin()
    @PutMapping("answers/{id}")
    protected Answer updateAnswer(
            @PathVariable Long id,
            @RequestBody AnswerInput answerInput
    ) {
        Answer answer = getAnswer(id);
        HelperFunctions.copyProperties(answer, answerInput);
        return answerRepository.save(answer);
    }
}
