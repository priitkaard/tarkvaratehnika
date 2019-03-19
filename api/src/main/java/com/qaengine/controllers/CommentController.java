package com.qaengine.controllers;

import com.qaengine.database.CommentRepository;
import com.qaengine.lib.HelperFunctions;
import com.qaengine.models.Answer;
import com.qaengine.models.Comment;
import com.qaengine.models.Question;
import com.qaengine.models.inputs.CommentInput;
import com.qaengine.services.AnswerService;
import com.qaengine.services.CommentService;
import com.qaengine.services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class CommentController {
    private CommentRepository commentRepository;
    private QuestionService questionService;
    private AnswerService answerService;
    private CommentService commentService;

    @Autowired
    public CommentController(
            CommentRepository commentRepository,
            QuestionService questionService,
            AnswerService answerService,
            CommentService commentService
    ) {
        this.commentRepository = commentRepository;
        this.questionService = questionService;
        this.answerService = answerService;
        this.commentService = commentService;
    }

    @PostMapping("/question/{questionId}/comment")
    protected Comment commentQuestion(
            @PathVariable Long questionId,
            @RequestBody @Valid CommentInput commentInput
    ) {
        Question question = questionService.getQuestion(questionId);
        Comment comment = new Comment();
        comment.setQuestion(question);

        HelperFunctions.copyProperties(comment, commentInput);
        return commentRepository.save(comment);
    }

    @PostMapping("answer/{answerId}/comment")
    protected Comment commentAnswer(
            @PathVariable Long answerId,
            @RequestBody @Valid CommentInput commentInput
    ) {
        Answer answer = answerService.getAnswer(answerId);
        Comment comment = new Comment();
        comment.setAnswer(answer);

        HelperFunctions.copyProperties(comment, commentInput);
        return commentRepository.save(comment);
    }

    @GetMapping("/comment/{id}")
    protected Comment getComment(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @PutMapping("/comment/{commentId}")
    protected Comment updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentInput commentInput
    ) {
        Comment comment = commentService.getCommentById(commentId);
        HelperFunctions.copyProperties(comment, commentInput);
        return commentRepository.save(comment);
    }

    @DeleteMapping("/comment/{commentId}")
    protected Long deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return commentId;
    }


    @PutMapping("/comment/{id}/upvote")
    protected Comment upvoteComment(
            @PathVariable Long id
    ) {
        Comment comment = commentService.getCommentById(id);
        comment.setScore(comment.getScore() + 1);
        return commentRepository.save(comment);
    }

    @PutMapping("/comment/{id}/downvote")
    protected Comment downvoteComment(@PathVariable Long id) {
        Comment comment = commentService.getCommentById(id);
        comment.setScore(comment.getScore() - 1);
        return commentRepository.save(comment);
    }
}
