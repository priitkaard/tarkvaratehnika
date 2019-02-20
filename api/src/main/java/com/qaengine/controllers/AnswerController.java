package com.qaengine.controllers;

import com.qaengine.database.AnswerRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.exceptions.BadRequestException;
import com.qaengine.exceptions.InternalServerErrorException;
import com.qaengine.exceptions.ResourceNotFoundException;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Answer;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.AnswerInput;
import com.qaengine.services.AnswerService;
import com.qaengine.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
public class AnswerController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    AnswerRepository answerRepository;
    @Autowired
    AnswerService answerService;
    @Autowired
    QuestionService questionService;

    @CrossOrigin()
    @PostMapping("questions/{questionId}/answers")
    protected Answer addAnswer(
            @PathVariable Long questionId,
            @RequestBody @Valid AnswerInput answerinput
    ) {
        Question question = questionService.getQuestion(questionId);
        Answer answer = new Answer(questionId);
        HelperFunctions.copyProperties(answer, answerinput);
        answer = answerRepository.save(answer);
        question.getAnswers().add(answer);
        questionRepository.save(question);
        return answer;
    }


    @CrossOrigin()
    @GetMapping("answers/{id}")
    protected Answer getAnswer(@PathVariable Long id) {
        return answerService.getAnswer(id);
    }

    @CrossOrigin()
    @DeleteMapping("answers/{id}")
    protected Long deleteAnswer(@PathVariable Long id) {
        Answer answer = getAnswer(id);
        Question question = questionService.getQuestion(answer.getQuestionId());
        question.getAnswers().remove(answer);
        questionRepository.save(question);
        return id;
    }

    @CrossOrigin()
    @PutMapping("answers/{id}")
    protected Answer updateAnswer(
            @PathVariable Long id,
            @RequestBody @Valid AnswerInput answerInput
    ) {
        Answer answer = getAnswer(id);
        HelperFunctions.copyProperties(answer, answerInput);
        return answerRepository.save(answer);
    }

    @CrossOrigin()
    @PutMapping("answers/{id}/upvote")
    protected Answer upvoteAnswer(
            @PathVariable Long id
    ) {
        Answer answer = getAnswer(id);
        answer.setScore(answer.getScore() + 1);
        return answerRepository.save(answer);
    }

    @CrossOrigin()
    @PutMapping("answers/{id}/downvote")
    protected Answer downvoteAnswer(
            @PathVariable Long id
    ) {
        Answer answer = getAnswer(id);
        answer.setScore(answer.getScore() - 1);
        return answerRepository.save(answer);
    }


    @CrossOrigin()
    @PutMapping("answers/{answerId}/accept")
    protected Answer acceptAnswer(
            @PathVariable Long answerId
    ) {
        Answer answer = answerService.getAnswer(answerId);
        Question question = questionService.getQuestion(answer.getQuestionId());
        if (!answer.getQuestionId().equals(answer.getQuestionId())) {
            throw new BadRequestException("Answer does not belong to specified question.");
        }
        questionRepository.revertAnswerAccepted(question.getId());
        answer.setAccepted(true);
        return answerRepository.save(answer);
    }

    @CrossOrigin()
    @PutMapping("questions/{questionId}/revertAnswerAccepted")
    protected Question revertAnswerAccepted(
            @PathVariable Long questionId
    ) {
        Question question = questionService.getQuestion(questionId);
        questionRepository.revertAnswerAccepted(question.getId());
        return question;
    }

}
