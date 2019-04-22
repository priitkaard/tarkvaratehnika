package com.qaengine.controllers;

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
    private AnswerService answerService;
    private QuestionService questionService;
    private UserService userService;

    @Autowired
    public AnswerController(
            AnswerService answerService,
            QuestionService questionService,
            UserService userService) {
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
        return answerService.saveAnswer(user, question, answerinput);
    }


    @GetMapping("/answer/{id}")
    public Answer getAnswer(@PathVariable Long id) {
        return answerService.getAnswer(id);
    }

    @DeleteMapping("/answers/{id}")
    public Long deleteAnswer(@PathVariable Long id) {
        return answerService.deleteAnswer(id);
    }

    @PutMapping("/answer/{id}")
    public Answer updateAnswer(
            @PathVariable Long id,
            @RequestBody @Valid AnswerInput answerInput
    ) {
        return answerService.updateAnswer(id, answerInput);
    }

    @PutMapping("/answer/{id}/upvote")
    public Answer upvoteAnswer(
            @PathVariable Long id
    ) {
        return answerService.upvoteAnswer(id);
    }

    @PutMapping("/answer/{id}/downvote")
    public Answer downvoteAnswer(
            @PathVariable Long id
    ) {
        return answerService.downvoteAnswer(id);
    }


    @PutMapping("/answer/{answerId}/accept")
    protected Answer acceptAnswer(
            @PathVariable Long answerId
    ) {
        return answerService.acceptAnswer(answerId);
    }

    @PutMapping("/question/{questionId}/revertAnswerAccepted")
    protected Question revertAnswerAccepted(
            @PathVariable Long questionId
    ) {
        return answerService.revertAnswerAccepted(questionId);
    }
}
