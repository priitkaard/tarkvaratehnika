package com.qaengine.controllers;

import com.qaengine.exceptions.BadRequestException;
import com.qaengine.models.Answer;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.DTO.AnswerDTO;
import com.qaengine.models.Question;
import com.qaengine.models.Vote;
import com.qaengine.services.AnswerService;
import com.qaengine.services.QuestionService;
import com.qaengine.services.UserService;
import com.qaengine.services.VoteService;
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
    private VoteService voteService;

    @Autowired
    public AnswerController(
            AnswerService answerService,
            QuestionService questionService,
            UserService userService,
            VoteService voteService) {
        this.answerService = answerService;
        this.questionService = questionService;
        this.userService = userService;
        this.voteService = voteService;
    }

    @PostMapping("question/{questionId}/answer")
    public Answer answerQuestion (
            @PathVariable Long questionId,
            @RequestBody @Valid AnswerDTO answerinput,
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
    public Long deleteAnswer(@PathVariable Long id, Principal principal) {
        if (!answerService.getAnswer(id).getUser().getUsername().equals(principal.getName())) {
            throw new BadRequestException("Permission denied");
        }
        return answerService.deleteAnswer(id);
    }

    @PutMapping("/answer/{id}")
    public Answer updateAnswer(
            @PathVariable Long id,
            @RequestBody @Valid AnswerDTO answerInput,
            Principal principal
    ) {
        if (!answerService.getAnswer(id).getUser().getUsername().equals(principal.getName())) {
            throw new BadRequestException("Permission denied");
        }
        return answerService.updateAnswer(id, answerInput);
    }

    @PutMapping("/answer/{id}/upvote")
    public Vote upvoteAnswer(
            @PathVariable Long id,
            Principal principal
    ) {
        ApplicationUser user = userService.getUser(principal.getName());
        return voteService.voteAnswer(id, user, 1);
    }

    @PutMapping("/answer/{id}/downvote")
    public Vote downvoteAnswer(
            @PathVariable Long id,
            Principal principal
    ) {
        ApplicationUser user = userService.getUser(principal.getName());
        return voteService.voteAnswer(id, user, -1);
    }


    @PutMapping("/answer/{answerId}/accept")
    protected Answer acceptAnswer(
            @PathVariable Long answerId,
            Principal principal
    ) {
        if (!answerService.getAnswer(answerId).getQuestion().getUser().getUsername().equals(principal.getName())) {
            throw new BadRequestException("Permission denied");
        }
        return answerService.acceptAnswer(answerId);
    }

    @PutMapping("/question/{questionId}/revertAnswerAccepted")
    protected Question revertAnswerAccepted(
            @PathVariable Long questionId,
            Principal principal
    ) {
        if (!questionService.getQuestion(questionId).getUser().getUsername().equals(principal.getName())) {
            throw new BadRequestException("Permission denied");
        }
        return answerService.revertAnswerAccepted(questionId);
    }
}
