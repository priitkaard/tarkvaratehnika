package com.qaengine.controllers;

import com.qaengine.database.AnswerRepository;
import com.qaengine.database.QuestionRepository;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Answer;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.AnswerInput;
import com.qaengine.services.AnswerService;
import com.qaengine.services.QuestionService;
import com.qaengine.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.security.Principal;

@RestController
public class AnswerController {
    private QuestionRepository questionRepository;
    private AnswerRepository answerRepository;
    private AnswerService answerService;
    private QuestionService questionService;
    private UserService userService;

    @Autowired
    public AnswerController(
            QuestionRepository questionRepository,
            AnswerRepository answerRepository,
            AnswerService answerService,
            QuestionService questionService,
            UserService userService) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.answerService = answerService;
        this.questionService = questionService;
        this.userService = userService;
    }

    @PostMapping("question/{questionId}/answer")
    public Answer answerQuestion (
            @PathVariable Long questionId,
            @RequestBody @Valid AnswerInput answerinput,
            Principal principal
    ) {
        ApplicationUser user = userService.getUser(principal.getName());
        Question question = questionService.getQuestion(questionId);

        Answer answer = new Answer();
        HelperFunctions.copyProperties(answer, answerinput);
        answer.setQuestion(question);
        answer.setUser(user);

        return answerRepository.save(answer);
    }


    @GetMapping("/answer/{id}")
    public Answer getAnswer(@PathVariable Long id) {
        return answerService.getAnswer(id);
    }

    @DeleteMapping("/answers/{id}")
    public Long deleteAnswer(@PathVariable Long id) {
        Answer answer = answerService.getAnswer(id);
        answerRepository.delete(answer);
        return id;
    }

    @PutMapping("/answer/{id}")
    protected Answer updateAnswer(
            @PathVariable Long id,
            @RequestBody @Valid AnswerInput answerInput
    ) {
        Answer answer = answerService.getAnswer(id);
        HelperFunctions.copyProperties(answer, answerInput);
        return answerRepository.save(answer);
    }

    @PutMapping("/answer/{id}/upvote")
    protected Answer upvoteAnswer(
            @PathVariable Long id
    ) {
        Answer answer = getAnswer(id);
        answer.setScore(answer.getScore() + 1);
        return answerRepository.save(answer);
    }

    @PutMapping("/answer/{id}/downvote")
    protected Answer downvoteAnswer(
            @PathVariable Long id
    ) {
        Answer answer = getAnswer(id);
        answer.setScore(answer.getScore() - 1);
        return answerRepository.save(answer);
    }


    @PutMapping("/answer/{answerId}/accept")
    protected Answer acceptAnswer(
            @PathVariable Long answerId
    ) {
        Answer answer = answerService.getAnswer(answerId);

        Question question = answer.getQuestion();
        questionRepository.revertAnswerAccepted(question);

        answer.setAccepted(true);
        return answerRepository.save(answer);
    }

    @PutMapping("/question/{questionId}/revertAnswerAccepted")
    protected Question revertAnswerAccepted(
            @PathVariable Long questionId
    ) {
        Question question = questionService.getQuestion(questionId);
        questionRepository.revertAnswerAccepted(question);
        return question;
    }
}
