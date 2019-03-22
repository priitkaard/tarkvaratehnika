package com.qaengine.controllers;

import com.qaengine.database.UserRepository;
import com.qaengine.models.outputs.Statistics;
import com.qaengine.services.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/statistics")
public class StatisticsController {
    private QuestionService questionService;
    private UserService userService;
    private AnswerService answerService;
    private CommentService commentService;

    public StatisticsController(QuestionService questionService,
                                UserService userService,
                                AnswerService answerService,
                                CommentService commentService) {
        this.questionService = questionService;
        this.userService = userService;
        this.answerService = answerService;
        this.commentService = commentService;
    }

    @GetMapping()
    public Statistics getStatistics(@RequestParam(required = false) Long category) {
        Long users = userService.getTotalUsers();
        Long questions = questionService.getTotalQuestions(Optional.ofNullable(category));
        Long answers = answerService.getTotalAnswers(Optional.ofNullable(category));
        Long comments = commentService.getTotalComments(Optional.ofNullable(category));
        return new Statistics(
                questions,
                answers,
                comments,
                users
        );
    }
}
