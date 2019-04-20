package com.qaengine.controllers;

import com.qaengine.models.Answer;
import com.qaengine.models.ApplicationUser;
import com.qaengine.models.Comment;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.CommentInput;
import com.qaengine.services.AnswerService;
import com.qaengine.services.CommentService;
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
public class CommentController {
    private QuestionService questionService;
    private AnswerService answerService;
    private CommentService commentService;
    private UserService userService;

    @Autowired
    public CommentController(
            QuestionService questionService,
            AnswerService answerService,
            CommentService commentService,
            UserService userService) {
        this.questionService = questionService;
        this.answerService = answerService;
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping("/question/{questionId}/comment")
    public Comment commentQuestion(
            @PathVariable Long questionId,
            @RequestBody @Valid CommentInput commentInput,
            Principal principal
    ) {
        ApplicationUser user = userService.getUser(principal.getName());
        Question question = questionService.getQuestion(questionId);

        return commentService.commentQuestion(user, question, commentInput);
    }

    @PostMapping("answer/{answerId}/comment")
    public Comment commentAnswer(
            @PathVariable Long answerId,
            @RequestBody @Valid CommentInput commentInput,
            Principal principal
    ) {
        ApplicationUser user = userService.getUser(principal.getName());
        Answer answer = answerService.getAnswer(answerId);

        return commentService.commentAnswer(user, answer, commentInput);
    }

    @GetMapping("/comment/{id}")
    public Comment getComment(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PutMapping("/comment/{commentId}")
    public Comment updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentInput commentInput
    ) {
        return commentService.updateComment(commentId, commentInput);
    }

    @DeleteMapping("/comment/{commentId}")
    public Long deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return commentId;
    }


    @PutMapping("/comment/{id}/upvote")
    public Comment upvoteComment(
            @PathVariable Long id
    ) {
        return commentService.upvoteComment(id);

    }

    @PutMapping("/comment/{id}/downvote")
    public Comment downvoteComment(@PathVariable Long id) {
        return commentService.downvoteComment(id);
    }
}
